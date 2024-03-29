package model;

import java.io.IOException;
import java.util.List;

public interface Loadable {
    public List<String> load(String file) throws IOException;
}
