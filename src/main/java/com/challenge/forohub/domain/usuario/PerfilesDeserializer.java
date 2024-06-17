package com.challenge.forohub.domain.usuario;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class PerfilesDeserializer extends JsonDeserializer {

    @Override
    public Perfiles deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException, JacksonException {

        // Leer el valor y convertir a minusculas
        String lowercaseValue = jsonParser.readValueAs(String.class).toLowerCase();
        // Convertir a mayuscula y obtener el valor del enum
        return Perfiles.valueOf(lowercaseValue.toUpperCase());
    }


}
