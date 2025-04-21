package com.device.api.config.web;

import io.hypersistence.tsid.TSID;
import org.springframework.core.convert.converter.Converter;

public class StringToTSIDWebConverter implements Converter<String, TSID> {

    @Override
    public TSID convert(String source) {
        // na api o TSID é string, e no banco é long
        // no caso, de buscar por string
        return TSID.from(source);

        // sugestão do copilot
        // no caso de buscar por long (se pegar o id no banco)
        /*try {
            return TSID.from(Long.parseLong(source));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor inválido para TSID: " + source, e);
        }*/
    }
}
