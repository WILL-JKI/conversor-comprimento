package br.com.willjki.conversor.service;

import org.apache.commons.lang3.StringUtils;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import java.util.HashMap;
import java.util.Map;

public class ConversorService {

    private static final Map<String, Unit<Length>> UNIDADES = new HashMap<>();

    static {
        UNIDADES.put("metros", Units.METRE);
        UNIDADES.put("metro", Units.METRE);
        UNIDADES.put("m", Units.METRE);
        
        UNIDADES.put("kilometros", Units.METRE.multiply(1000));
        UNIDADES.put("quilometros", Units.METRE.multiply(1000));
        UNIDADES.put("km", Units.METRE.multiply(1000));
        
        UNIDADES.put("centimetros", Units.METRE.divide(100));
        UNIDADES.put("centímetros", Units.METRE.divide(100));
        UNIDADES.put("cm", Units.METRE.divide(100));
        
        UNIDADES.put("milimetros", Units.METRE.divide(1000));
        UNIDADES.put("milímetros", Units.METRE.divide(1000));
        UNIDADES.put("mm", Units.METRE.divide(1000));
        
        // Unidades imperiais
        UNIDADES.put("milhas", Units.METRE.multiply(1609.34));
        UNIDADES.put("milha", Units.METRE.multiply(1609.34));
        
        UNIDADES.put("polegadas", Units.METRE.multiply(0.0254));
        UNIDADES.put("polegada", Units.METRE.multiply(0.0254));
        UNIDADES.put("in", Units.METRE.multiply(0.0254));
        
        UNIDADES.put("pes", Units.METRE.multiply(0.3048));
        UNIDADES.put("pés", Units.METRE.multiply(0.3048));
        UNIDADES.put("ft", Units.METRE.multiply(0.3048));
    }

    public double converter(double valor, String unidadeDe, String unidadePara) {
        // Validação usando Apache Commons Lang3
        if (StringUtils.isBlank(unidadeDe) || StringUtils.isBlank(unidadePara)) {
            throw new IllegalArgumentException("Unidades não podem ser vazias");
        }

        String deNormalizado = unidadeDe.toLowerCase().trim();
        String paraNormalizado = unidadePara.toLowerCase().trim();

        Unit<Length> unitDe = UNIDADES.get(deNormalizado);
        Unit<Length> unitPara = UNIDADES.get(paraNormalizado);

        if (unitDe == null) {
            throw new IllegalArgumentException("Unidade de origem desconhecida: " + unidadeDe);
        }
        if (unitPara == null) {
            throw new IllegalArgumentException("Unidade de destino desconhecida: " + unidadePara);
        }

        // Conversão usando Indriya
        Quantity<Length> quantidade = Quantities.getQuantity(valor, unitDe);
        Quantity<Length> convertido = quantidade.to(unitPara);

        return convertido.getValue().doubleValue();
    }
}
