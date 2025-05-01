package NoteClasses;
import com.example.thenotelobster.model.NoteClasses.Note;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {

    Note note;
    @BeforeEach
    public void setUp() {
        note = new Note(1, "TestNote", "There is lots of text here");
    }

    @Test
    public void testGetNoteId() {
        assertEquals(1, note.getNoteId());
    }

    @Test
    public void testGetName() {
        assertEquals("TestNote", note.getName());
    }

    @Test
    public void testGetText() {
        assertEquals("There is lots of text here", note.getText());
    }

}
