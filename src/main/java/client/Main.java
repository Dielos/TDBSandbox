package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.shared.Lock;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.tdb.TDBLoader;
import org.apache.jena.tdb.base.file.Location;
import org.apache.jena.tdb.store.DatasetGraphTDB;
import org.apache.jena.tdb.sys.TDBInternal;
import org.apache.jena.util.FileManager;

/**
 *
 * @author richou
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Location location = Location.create("E:\\Users\\richou\\src\\TDBSandbox\\tdb");
        Dataset dataset = TDBFactory.createDataset(location);

        importOwl(dataset, new FileInputStream("E:\\Users\\richou\\src\\TDBSandbox\\resources\\sandbox.owl"));
        Model model = readModel(dataset);
        for(StmtIterator i = model.listStatements() ; i.hasNext() ;) {
            System.out.println(i.nextStatement().toString());
        }
    }
    
    private static void importOwl(Dataset dataset, InputStream in) {
        dataset.begin(ReadWrite.WRITE);
        // Get model inside the transaction
        Model model = dataset.getDefaultModel();
        
        model.read(in, null, null);

        dataset.commit();
        dataset.end();
    }
    
    private static Model readModel(Dataset dataset) {
        dataset.begin(ReadWrite.READ);
        // Get model inside the transaction
        Model model = dataset.getDefaultModel();
        dataset.end();
        
        return model;
    }
}
