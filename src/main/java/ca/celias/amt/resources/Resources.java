package ca.celias.amt.resources;

import javax.enterprise.inject.Produces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.celias.amt.dto.PatchValue;
import ca.celias.amt.dto.PatchValueTypeAdapter;

/**
 *
 * @author Chris Elias
 */
public class Resources {

    @Produces
    @JsonPatch
    public Gson produceGsonPatch() {
        return new GsonBuilder()
                .registerTypeAdapter(PatchValue.class, new PatchValueTypeAdapter())
                .create();
    }
}
