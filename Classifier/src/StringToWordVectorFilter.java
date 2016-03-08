import org.apache.commons.io.FilenameUtils;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.stemmers.SnowballStemmer;
import weka.core.tokenizers.WordTokenizer;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.File;

/**
 * Created by Haihui on 5/3/2016.
 */
public class StringToWordVectorFilter {

    public static final String STOPWORDS_FILEPATH = "files/stop-words.txt";

    /**
     * Extract word features using the StringToWordVector filter, save it to ARFF file, and serialize the filter.
     * @param filename The full path of the ARFF file.
     * @param classIndex The index of the class attribute.
     * @throws Exception
     */
    public static void filter(String filename, int classIndex) throws Exception {

        Instances inputInstances = ArffFileManager.loadARFF(filename, classIndex);
        Instances outputInstances;

        //Set the filter.
        StringToWordVector filter = setUpStringToWordVectorFilter(inputInstances);

        //Filter the input instances into the output ones.
        outputInstances = Filter.useFilter(inputInstances, filter);

        //Save to an ARFF file.
        String basename = FilenameUtils.getBaseName(filename);
        String path = FilenameUtils.getPath(filename);
        ArffFileManager.saveARFF(outputInstances, path + basename + "-filtered.arff");

        //Serialize the filter.
        SerializationHelper.write("files/string-to-word-vector-filter.model", filter);
    }

    /**
     * Extract word features using the pre-built StringToWordVector filter.
     * @param arffFile The full path of the ARFF file to be filtered.
     * @param classIndex  The index of the class attribute.
     * @param filterFile The full path of the filter file.
     * @return The output instances.
     * @throws Exception
     */
    public static Instances filter(String arffFile, int classIndex, String filterFile) throws Exception {

        Instances inputInstances = ArffFileManager.loadARFF(arffFile, classIndex);
        Instances outputInstances;

        //Set the filter.
        StringToWordVector filter = (StringToWordVector) SerializationHelper.read(filterFile);

        //Filter the input instances into the output ones.
        outputInstances = Filter.useFilter(inputInstances, filter);

        return outputInstances;
    }


    /**
     * Set up the StringToWordFilter.
     * @param inputInstances The Instances object with text.
     * @return The filter.
     */
    private static StringToWordVector setUpStringToWordVectorFilter(Instances inputInstances) throws Exception {

        //Set the tokenizer.
        WordTokenizer tokenizer = new WordTokenizer();

        //Set the StringToWordVector filter.
        StringToWordVector filter = new StringToWordVector();
        SnowballStemmer stemmer = new SnowballStemmer();
        filter.setTokenizer(tokenizer);
        filter.setInputFormat(inputInstances);
        filter.setWordsToKeep(1000000);
        filter.setDoNotOperateOnPerClassBasis(true);
        filter.setLowerCaseTokens(true);
        filter.setStemmer(stemmer);
        filter.setStopwords(new File(STOPWORDS_FILEPATH));

        return filter;
    }
}
