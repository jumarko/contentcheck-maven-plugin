package net.sf.buildbox.maven.contentcheck;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class CheckerOutputTest {
    
    private CheckerOutput output;
    
    @Before
    public void setUp() {
        Set<String> allowedEntries = new LinkedHashSet<String>() {
            {
                add("a.jar");
                add("b.jar");
                add("c.jar");
            }
        };
        
        Set<String> archiveContent = new LinkedHashSet<String>() {
            {
                add("a.jar");
                add("d.jar");
            }
        };
        
        this.output = new CheckerOutput(allowedEntries, archiveContent);
    }

    @Test
    public void testGetAllowedEntries() {
        assertThat(output.getAllowedEntries(), notNullValue());
        assertThat(output.getAllowedEntries().size(), is(3));
	}

    @Test
    public void testGetArchiveEntries() {
        assertThat(output.getArchiveEntries(), notNullValue());
        assertThat(output.getArchiveEntries().size(), is(2));
    }

    @Test
    public void testDiffUnexpectedEntries() {
        Set<String> unexpectedEntries = output.diffUnexpectedEntries();
        assertThat(unexpectedEntries, notNullValue());
        assertThat("Wrong count of unexpected entries.", unexpectedEntries.size(), is(1));
        assertThat("Concrete unexpected entry is missing.", unexpectedEntries.contains("d.jar"), is(true));
    }

    @Test
    public void testDiffMissingEntries() {
        Set<String> missingEntries = output.diffMissingEntries();
        assertThat(missingEntries, notNullValue());
        assertThat("Wrong count of missing entries.", missingEntries.size(), is(2));
        assertThat(missingEntries.contains("b.jar"), is(true));
        assertThat(missingEntries.contains("c.jar"), is(true));
    }
}