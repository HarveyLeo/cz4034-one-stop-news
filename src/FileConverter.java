/**
 * Created by Haihui on 5/3/2016.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import org.json.JSONArray;
import org.json.CDL;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;


public class FileConverter {

    public static final String URL_REGEX = "(https?://)?([\\da-z.-]+)\\.([a-z.]*)([/\\w.-]*)*/?";
    public static final String NON_LETTER_SPACE_REGEX = "[^A-Za-z ]";
    public static final String SPACE_REGEX = "\\s+";


    /**
     * Generate ARFF file given the JSON file.
     * @param filename The full path of a JSON file.
     * @param remove Remove URL, non-letter character and redundant spaces if set to true.
     * @throws Exception
     */
    public static String convertJSONtoARFF(String filename, boolean remove) throws Exception {
        String csvFilename, arffFilename;
        csvFilename = convertJSONtoCSV(filename, remove);
        arffFilename = convertCSVtoARFF(csvFilename);
        return arffFilename;
    }

    /**
     * Generate CSV file given the JSON file.
     * @param filename The full path of a JSON file.
     * @param remove Remove URL, non-letter character and redundant spaces if set to true.
     * @return The CSV file name.
     * @throws Exception
     */
    public static String convertJSONtoCSV(String filename, boolean remove) throws Exception {

        //Read the JSON file into a JSON array.
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String jsonString = IOUtils.toString(br);
        JSONArray jsonArray = new JSONArray(jsonString);

        //Remove URL, non-letter character and redundant spaces
        if (remove) {
            JSONObject jsonObject;
            String message;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                message = jsonObject.getString("message");
                message = message.replaceAll(URL_REGEX, " ").replaceAll(NON_LETTER_SPACE_REGEX, " ");
                message = message.replaceAll(SPACE_REGEX, " ").trim();
                jsonObject.put("message", message);
            }
        }

        //Convert the JSON array to CSV string.
        String csv = CDL.toString(jsonArray);

        //Write the CSV string into .csv file.
        String csvFilename = FilenameUtils.getPath(filename) + FilenameUtils.getBaseName(filename) + ".csv";
        FileWriter fw = new FileWriter(csvFilename);
        IOUtils.write(csv, fw);
        IOUtils.closeQuietly(fw);

        return csvFilename;
    }

    /**
     * Generate CSV file given the ARFF file.
     * @param filename The full path of a CSV file.
     * @return The ARFF file name.
     * @throws Exception
     */
    public static String convertCSVtoARFF(String filename) throws Exception {

       //Load CSV.
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(filename));
        Instances data = loader.getDataSet();

        //Save ARFF.
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        String arffFilename = FilenameUtils.getPath(filename) + FilenameUtils.getBaseName(filename) + ".arff";
        saver.setFile(new File(arffFilename));
        saver.writeBatch();

        return arffFilename;
    }

    /**
     * Generate ARFF file given the CSV file.
     * @param filename The full path of a ARFF file.
     * @return The CSV file name.
     * @throws Exception
     */
    public static String convertARFFtoCSV(String filename) throws Exception {

        //Load ARFF.
        ArffLoader loader = new ArffLoader();
        loader.setSource(new File(filename));
        Instances data = loader.getDataSet();

        //Save ARFF.
        CSVSaver saver = new CSVSaver();
        saver.setInstances(data);
        String csvFilename = FilenameUtils.getPath(filename) + FilenameUtils.getBaseName(filename) + ".csv";
        saver.setFile(new File(csvFilename));
        saver.writeBatch();

        return csvFilename;
    }

}
