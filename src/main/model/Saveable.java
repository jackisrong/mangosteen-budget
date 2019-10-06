package model;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public interface Saveable {
    public void save(ArrayList<String> content, String file) throws FileNotFoundException, UnsupportedEncodingException;
}
