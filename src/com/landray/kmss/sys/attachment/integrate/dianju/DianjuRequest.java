package com.landray.kmss.sys.attachment.integrate.dianju;


import java.util.Enumeration;
import java.util.Hashtable;

public class DianjuRequest {
    private Hashtable m_parameters = new Hashtable();
    private int m_counter = 0;

    DianjuRequest() {
    }

    protected void putParameter(String s, String s1) {
        if (s == null) {
            throw new IllegalArgumentException("The name of an element cannot be null.");
        } else {
            Hashtable hashtable;
            if (this.m_parameters.containsKey(s)) {
                hashtable = (Hashtable)this.m_parameters.get(s);
                hashtable.put(new Integer(hashtable.size()), s1);
            } else {
                hashtable = new Hashtable();
                hashtable.put(new Integer(0), s1);
                this.m_parameters.put(s, hashtable);
                ++this.m_counter;
            }

        }
    }

    public String getParameter(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Form's name is invalid or does not exist (1305).");
        } else {
            Hashtable hashtable = (Hashtable)this.m_parameters.get(s);
            return hashtable == null ? null : (String)hashtable.get(new Integer(0));
        }
    }

    public Enumeration getParameterNames() {
        return this.m_parameters.keys();
    }

    public String[] getParameterValues(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Form's name is invalid or does not exist (1305).");
        } else {
            Hashtable hashtable = (Hashtable)this.m_parameters.get(s);
            if (hashtable == null) {
                return null;
            } else {
                String[] as = new String[hashtable.size()];

                for(int i = 0; i < hashtable.size(); ++i) {
                    as[i] = (String)hashtable.get(new Integer(i));
                }

                return as;
            }
        }
    }
}
