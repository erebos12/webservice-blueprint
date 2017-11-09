package com.bisnode.bhc.domain;

import org.hamcrest.core.IsNull;
import org.junit.Test;

import static com.bisnode.bhc.domain.portfolio.GlobalMapping.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GlobalMappingTest {

    @Test
    public void test_convert_systemIdName_to_Integer() {
        Integer i = getSystemIdValue("PBC");
        assertThat(i, is(2));
        i = getSystemIdValue("P2R");
        assertThat(i, is(1));
        i = getSystemIdValue("P4S");
        assertThat(i, is(3));
        i = getSystemIdValue("uuu");
        assertThat(i, IsNull.nullValue());
    }

    @Test
    public void test_converte_systemId_to_Name() {
        String s = getSystemNameValue(1);
        assertThat(s, is("P2R"));
        s = getSystemNameValue(2);
        assertThat(s, is("PBC"));
        s = getSystemNameValue(3);
        assertThat(s, is("P4S"));
        s = getSystemNameValue(0);
        assertThat(s, IsNull.nullValue());
    }

    @Test
    public void test_convert_ProfileName_to_ProfileID() {
        Integer i = getProfileIdValue("SMALL");
        assertThat(i, is(1));
        i = getProfileIdValue("MEDIUM");
        assertThat(i, is(2));
        i = getProfileIdValue("LARGE");
        assertThat(i, is(3));
        i = getProfileIdValue("ggg");
        assertThat(i, IsNull.nullValue());
    }

    @Test
    public void test_convert_ProfileID_toProfileName() {
        String s = getProfileNameValue(1);
        assertThat(s, is("SMALL"));
        s = getProfileNameValue(2);
        assertThat(s, is("MEDIUM"));
        s = getProfileNameValue(3);
        assertThat(s, is("LARGE"));
        s = getSystemNameValue(0);
        assertThat(s, IsNull.nullValue());
    }

}
