package bt.org.dsp.crst.base;

import org.hibernate.HibernateException;
import org.hibernate.PropertyNotFoundException;
import org.hibernate.property.access.internal.PropertyAccessStrategyBasicImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyChainedImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyFieldImpl;
import org.hibernate.property.access.internal.PropertyAccessStrategyMapImpl;
import org.hibernate.property.access.spi.Setter;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.lang.reflect.Field;
import java.util.Arrays;

public class CaseLessAliasToBeanResultTransformer extends AliasedTupleSubsetResultTransformer {
    private final Class resultClass;
    private boolean isInitialized;
    private String[] aliases;
    private Setter[] setters;
    private Field[] fields;

    public CaseLessAliasToBeanResultTransformer(Class resultClass) {
        if(resultClass == null) {
            throw new IllegalArgumentException("resultClass cannot be null");
        } else {
            this.isInitialized = false;
            this.resultClass = resultClass;
        }
    }

    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    public Object transformTuple(Object[] tuple, String[] aliases) {
        try {
            if(!this.isInitialized) {
                this.initialize(aliases);
            } else {
                this.check(aliases);
            }

            Object result = this.resultClass.newInstance();

            for(int e = 0; e < aliases.length; ++e) {
                if(this.setters[e] != null) {
                    this.setters[e].set(result, tuple[e],null);
                }
            }

            return result;
        } catch (InstantiationException | IllegalAccessException var5) {
            throw new HibernateException("Could not instantiate resultclass: " + this.resultClass.getName());
        }
    }

    private void initialize(String[] aliases) {
        PropertyAccessStrategyChainedImpl propertyAccessStrategy = new PropertyAccessStrategyChainedImpl(PropertyAccessStrategyBasicImpl.INSTANCE,
                PropertyAccessStrategyFieldImpl.INSTANCE, PropertyAccessStrategyMapImpl.INSTANCE);
        this.aliases = new String[aliases.length];
        this.setters = new Setter[aliases.length];
        this.fields  = this.resultClass.getDeclaredFields();

        for(int i = 0; i < aliases.length; ++i) {
            String alias = aliases[i];
            if(alias != null) {
                this.aliases[i] = alias;
                try{
                    this.setters[i] = propertyAccessStrategy.buildPropertyAccess(this.resultClass, alias).getSetter();
                } catch (final PropertyNotFoundException e) {
                    for (final Field field : fields) {
                        if (field.getName().equalsIgnoreCase(alias)) {
                            alias = field.getName();
                            break;
                        }
                    }
                    this.setters[i] = propertyAccessStrategy.buildPropertyAccess(resultClass, alias).getSetter();
                }
            }
        }
        this.isInitialized = true;
    }

    private void check(String[] aliases) {
        if(!Arrays.equals(aliases, this.aliases)) {
            throw new IllegalStateException("aliases are different from what is cached; aliases=" + Arrays.asList(aliases) + " cached=" + Arrays.asList(this.aliases));
        }
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            CaseLessAliasToBeanResultTransformer that = (CaseLessAliasToBeanResultTransformer)o;
            return !this.resultClass.equals(that.resultClass)?false:Arrays.equals(this.aliases, that.aliases);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.resultClass.hashCode();
        result = 31 * result + (this.aliases != null?Arrays.hashCode(this.aliases):0);
        return result;
    }
}
