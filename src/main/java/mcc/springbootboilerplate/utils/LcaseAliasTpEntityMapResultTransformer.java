package mcc.springbootboilerplate.utils;


import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.util.Map;
import java.util.TreeMap;

public class LcaseAliasTpEntityMapResultTransformer extends AliasedTupleSubsetResultTransformer {

    public static final LcaseAliasTpEntityMapResultTransformer INSTANCE = new LcaseAliasTpEntityMapResultTransformer();

    /**
     * Disallow instantiation of UcaseAliasTpEntityMapResultTransformer.
     */
    private LcaseAliasTpEntityMapResultTransformer() {
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for ( int i=0; i<tuple.length; i++ ) {
            String alias = aliases[i];
            if ( alias!=null ) {
                result.put(StringUtils.lowerCase(alias), tuple[i]);
            }
        }
        return result;
    }

    @Override
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    /**
     * Serialization hook for ensuring singleton uniqueing.
     *
     * @return The singleton instance : {@link #INSTANCE}
     */
    private Object readResolve() {
        return INSTANCE;
    }
}
