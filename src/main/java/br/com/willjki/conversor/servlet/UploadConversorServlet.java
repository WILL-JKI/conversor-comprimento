package br.com.willjki.conversor.servlet;

import br.com.willjki.conversor.service.ConversorService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UploadConversorServlet extends HttpServlet {

    private final ConversorService conversorService = new ConversorService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (!ServletFileUpload.isMultipartContent(request)) {
            out.print("{\"erro\": \"Requisição deve ser multipart/form-data\"}");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(1024 * 1024); // 1MB max
            
            List<FileItem> items = upload.parseRequest(request);
            
            for (FileItem item : items) {
                if (!item.isFormField() && "arquivo".equals(item.getFieldName())) {
                    String resultado = processarArquivo(item);
                    out.print(resultado);
                    return;
                }
            }
            
            out.print("{\"erro\": \"Nenhum arquivo encontrado\"}");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            
        } catch (Exception e) {
            out.print("{\"erro\": \"" + e.getMessage() + "\"}");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String processarArquivo(FileItem item) throws IOException {
        StringBuilder resultado = new StringBuilder("{\"conversoes\": [");
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(item.getInputStream(), StandardCharsets.UTF_8))) {
            
            String linha;
            boolean primeiro = true;
            int linhaNum = 0;
            
            while ((linha = reader.readLine()) != null) {
                linhaNum++;
                linha = linha.trim();
                
                if (StringUtils.isBlank(linha) || linha.startsWith("#")) {
                    continue;
                }
                
                String[] partes = linha.split(",");
                if (partes.length != 3) {
                    continue;
                }
                
                try {
                    double valor = Double.parseDouble(partes[0].trim());
                    String de = partes[1].trim().toLowerCase();
                    String para = partes[2].trim().toLowerCase();
                    
                    double valorConvertido = conversorService.converter(valor, de, para);
                    
                    if (!primeiro) resultado.append(",");
                    resultado.append(String.format(
                        "{\"linha\": %d, \"entrada\": \"%.4f %s\", \"resultado\": \"%.4f %s\"}",
                        linhaNum, valor, de, valorConvertido, para
                    ));
                    primeiro = false;
                    
                } catch (Exception e) {
                    if (!primeiro) resultado.append(",");
                    resultado.append(String.format(
                        "{\"linha\": %d, \"erro\": \"%s\"}",
                        linhaNum, e.getMessage().replace("\"", "'")
                    ));
                    primeiro = false;
                }
            }
        }
        
        resultado.append("]}");
        return resultado.toString();
    }
}
