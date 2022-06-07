package bt.org.dsp.crst.base;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class BaseDao {

    protected EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    protected Session getCurrentSession() {
        return em.unwrap(Session.class);
    }

    protected org.hibernate.Query hibernateQuery(String query, Class dtoClazz) {
        return getCurrentSession()
                .createSQLQuery(query)
                .setResultTransformer(new CaseLessAliasToBeanResultTransformer(dtoClazz));
    }

    protected org.hibernate.Query hibernateQuery(String query) {
        return getCurrentSession().createSQLQuery(query);
    }

    protected Query persistenceQuery(String query, Class entityClazz) {
        return em.createNativeQuery(query, entityClazz);
    }

    protected Query persistenceQuery(String query) {
        return em.createNativeQuery(query);
    }

    protected void saveOrUpdate(Object obj) {
        getCurrentSession().saveOrUpdate(obj);
    }

    protected void deleteE(Object obj) {
        em.remove(obj);
    }

}
