package com.alex.ws.domain;

import com.alex.ws.domain.portfolio.GlobalMapping;
import org.hamcrest.core.IsNull;
import org.junit.Test;

import static com.alex.bhc.domain.portfolio.GlobalMapping.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GlobalMappingTest {

    @Test
    public void test_convert_systemIdName_to_Integer() {
        Integer i = GlobalMapping.getSystemIdValue("PBC");
        assertThat(i, is(2));
        i = GlobalMapping.getSystemIdValue("P2R");
        assertThat(i, is(1));
        i = GlobalMapping.getSystemIdValue("P4S");
        assertThat(i, is(3));
        i = GlobalMapping.getSystemIdValue("uuu");
        assertThat(i, IsNull.nullValue());
    }

    @Test
    public void test_converte_systemId_to_Name() {
        String s = GlobalMapping.getSystemNameValue(1);
        assertThat(s, is("P2R"));
        s = GlobalMapping.getSystemNameValue(2);
        assertThat(s, is("PBC"));
        s = GlobalMapping.getSystemNameValue(3);
        assertThat(s, is("P4S"));
        s = GlobalMapping.getSystemNameValue(0);
        assertThat(s, IsNull.nullValue());
    }

    @Test
    public void test_convert_ProfileName_to_ProfileID() {
        Integer i = GlobalMapping.getProfileIdValue("SMALL");
        assertThat(i, is(1));
        i = GlobalMapping.getProfileIdValue("MEDIUM");
        assertThat(i, is(2));
        i = GlobalMapping.getProfileIdValue("LARGE");
        assertThat(i, is(3));
        i = GlobalMapping.getProfileIdValue("ggg");
        assertThat(i, IsNull.nullValue());
    }

    @Test
    public void test_convert_ProfileID_toProfileName() {
        String s = GlobalMapping.getProfileNameValue(1);
        assertThat(s, is("SMALL"));
        s = GlobalMapping.getProfileNameValue(2);
        assertThat(s, is("MEDIUM"));
        s = GlobalMapping.getProfileNameValue(3);
        assertThat(s, is("LARGE"));
        s = GlobalMapping.getSystemNameValue(0);
        assertThat(s, IsNull.nullValue());
    }

}
