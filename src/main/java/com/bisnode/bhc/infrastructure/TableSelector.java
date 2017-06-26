package com.bisnode.bhc.infrastructure;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bisnode.bhc.domain.Portfolio;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableSelector {

    private final HibernateAdapter hibernate;
    private static final Logger logger = LoggerFactory.getLogger(TableSelector.class);

    public TableSelector(URL configFile) throws IOException {
        hibernate = new HibernateAdapter(configFile, Arrays.asList(Portfolio.class));
    }

    /**
     * This method corresponds to SQL:
     * select * from TABLE
     * where COLUMN_X in (critX1, critX2, ..., critXn) = SelectColumnCriteria for COLUMN_X
     * and   COLUMN_Y in (critY1, critY2, ..., critYn) = SelectColumnCriteria for COLUMN_Y
     * and   ...
     * and   COLUMN_N in (critN1, critN2, ..., critNn) = SelectColumnCriteria for COLUMN_N
     *
     * @param dbTableClazz      class name of the DB table
     * @param selectColumnCriteriaList List of SelectColumnCriteria which represents - >=1 of COLUMN_X in (crit1, crit2, ..., critN)
     * @return List of results of type generic T
     */
    @SuppressWarnings("unchecked") // compiler just knows at runtime types of generic
    public <T> List<T> selectWhereInMultipleList(Class<?> dbTableClazz, List<SelectColumnCriteria> selectColumnCriteriaList) {
        List<T> result = new ArrayList<T>();
        try (Session session = hibernate.getSessionFactory().openSession()) {
            Criteria cr = session.createCriteria(dbTableClazz);
            selectColumnCriteriaList.stream().forEach(scc -> addConjunctionToCriteria(cr, scc));
            logger.info("Criterion: " + cr.toString());
            result = cr.list();
        }
        return result;
    }

    private void addConjunctionToCriteria(Criteria cr, SelectColumnCriteria scc) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.in(scc.getColumnName(), scc.getFilterList()));
        cr.add(conjunction);
    }
}
