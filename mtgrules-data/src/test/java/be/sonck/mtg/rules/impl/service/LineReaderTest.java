package be.sonck.mtg.rules.impl.service;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by johansonck on 13/07/15.
 */
public class LineReaderTest {

    private static class InputStreamReaderStub extends InputStreamReader {
        public boolean closed;

        public InputStreamReaderStub(InputStream in) {
            super(in);
        }

        @Override
        public void close() throws IOException {
            super.close();
            closed = true;
        }
    }

    @Test
    public void test() throws IOException, InterruptedException {
        InputStreamReaderStub stub = new InputStreamReaderStub(new ClassPathResource("/be/sonck/mtg/rules/LineReaderTestInput.txt").getInputStream());

        try (LineReader lineReader = new LineReader(stub)) {
            Iterator<String> iterator = lineReader.iterator();

            assertTrue(iterator.hasNext());
            assertThat(iterator.next(), is("1"));
            assertTrue(iterator.hasNext());
            assertThat(iterator.next(), is("2"));
            assertTrue(iterator.hasNext());
            assertThat(iterator.next(), is("3"));
            assertFalse(iterator.hasNext());

        } catch (IOException e) {
            throw e;
        }

        assertTrue(stub.closed);
    }
}