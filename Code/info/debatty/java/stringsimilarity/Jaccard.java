package info.debatty.java.stringsimilarity;

import info.debatty.java.stringsimilarity.interfaces.MetricStringDistance;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringSimilarity;
import info.debatty.java.stringsimilarity.interfaces.NormalizedStringDistance;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import info.debatty.java.stringsimilarity.Immutable;

/**
 * Each input string is converted into a set of n-grams, the Jaccard index is
 * then computed as |V1 inter V2| / |V1 union V2|.
 * Like Q-Gram distance, the input strings are first converted into sets of
 * n-grams (sequences of n characters, also called k-shingles), but this time
 * the cardinality of each n-gram is not taken into account.
 * Distance is computed as 1 - cosine similarity.
 * Jaccard index is a metric distance.
 * @author Thibault Debatty
 */
@Immutable
public class Jaccard extends ShingleBased implements
        MetricStringDistance, NormalizedStringDistance,
        NormalizedStringSimilarity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The strings are first transformed into sets of k-shingles (sequences of k
     * characters), then Jaccard index is computed as |A inter B| / |A union B|.
     * The default value of k is 3.
     *
     * @param k
     */
    public Jaccard(final int k) {
        super(k);
    }

    /**
     * The strings are first transformed into sets of k-shingles (sequences of k
     * characters), then Jaccard index is computed as |A inter B| / |A union B|.
     * The default value of k is 3.
     */
    public Jaccard() {
        super();
    }

    /**
     * Compute jaccard index: |A inter B| / |A union B|.
     * @param s1
     * @param s2
     * @return
     */
    public final double similarity(final String s1, final String s2) {
        Map<String, Integer> profile1 = getProfile(s1);
        Map<String, Integer> profile2 = getProfile(s2);

        Set<String> union = new HashSet<String>();
        union.addAll(profile1.keySet());
        union.addAll(profile2.keySet());

        int inter = 0;

        for (String key : union) {
            if (profile1.containsKey(key) && profile2.containsKey(key)) {
                inter++;
            }
        }

        return 1.0 * inter / union.size();
    }


    /**
     * Distance is computed as 1 - similarity.
     * @param s1
     * @param s2
     * @return
     */
    public final double distance(final String s1, final String s2) {
        return 1.0 - similarity(s1, s2);
    }
    
//    public static void main(String args[])
//    {
//    	Jaccard db=new Jaccard();
//    	System.out.println(db.similarity("13MP primary camera with auto focus, LED flash and 5MP front facing camera 12.7 centimeters (5-inch) HD IPS capacitive touchscreen with 1280 x 720 pixels resolution Android v5.1 Lollipop operating system with 1.4GHz 64-bit Qualcomm Snapdragon 415 octa core processor, Adreno 405 (up to 550MHz 3D graphics accelerator or up to 465MHz) GPU, 2GB RAM, 16GB internal memory expandable up to 32GB and dual SIM (micro+micro) dual-standby (4G+4G) 2750mAH lithium-ion battery providing talk-time of 32 hours on 2G, 15","8MP primary camera with auto mode, beauty face, continuous shot, interval shot, panorama mode and 5MP front facing camera with palm gesture selfie and 120 degree selfie mode 12.7 centimeters (5-inch) TFT capacitive touchscreen with 720 x 1280 pixels HD display Andorid v6.0 Marshmallow operating system with 1.3GHz Exynos quad core processor, 2GB RAM, 16GB internal memory expandable up to 128GB and dual SIM (micro+micro) dual-standby (4G+4G) 2600mAH lithium-ion battery 1 year manufacturer warranty for de"));
//    }
}
