package NoteClasses;
import com.example.thenotelobster.model.NoteClasses.Folder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FolderTest {

    Folder folder;
    @BeforeEach
    public void setUp() {
        folder = new Folder(2,"FolderName");
    }

    @Test
    public void testGetFolderId() {
        assertEquals(2, folder.getFolderId());
    }

    @Test
    public void testGetName() {
        assertEquals("FolderName", folder.getName());
    }


}
