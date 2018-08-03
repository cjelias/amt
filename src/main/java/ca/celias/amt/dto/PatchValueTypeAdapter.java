package ca.celias.amt.dto;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 *
 * @author Chris Elias
 */
public class PatchValueTypeAdapter extends TypeAdapter<PatchValue> {

    @Override
    public PatchValue read(JsonReader in) 
    throws IOException {
        var patchValue = new PatchValue();
        var jsonParser = new JsonParser();
        final JsonElement je = jsonParser.parse(in);

        if(je instanceof JsonPrimitive) {   
            patchValue.setNameArr(new String[1]);
            patchValue.getNameArr()[0] = ((JsonPrimitive)je).getAsString();
        } else if (je instanceof JsonArray) {
            var jsonArr = (JsonArray)je;
            patchValue.setNameArr(new String[jsonArr.size()]);
            
            for (int i = 0; i < jsonArr.size(); i++) {
                var jo = (JsonObject)jsonArr.get(i); 
                patchValue.getNameArr()[i] = jo.get("name").getAsString();
            }
        } else {
            patchValue = null;
        }

        return patchValue;
    }

    @Override
    public void write(JsonWriter out, PatchValue value) 
    throws IOException {
        if (value != null) {
            if (value.getNameArr() != null && value.getNameArr().length > 0) {
                if (value.getNameArr().length == 1) {
                    out.value(value.getNameArr()[0]);
                } else if (value.getNameArr().length > 1) {
                    out.beginArray();
                    
                    for (final String nameVal : value.getNameArr()) {
                        out.beginObject();
                        out.name("name").value(nameVal);
                        out.endObject();
                    }
                    
                    out.endArray();
                }
            }
        }
    }       
}