package ru.mail.polis.open.task6;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManagerImplTest {

    private static ManagerImpl manager;
    private static Book book;

    @BeforeAll
    static void createInstanceAndStartWorking() {
        manager = new ManagerImpl(
            "Petrov",
            "Artem",
            "Igorevich",
            'M',
            18,
            50000
        );

        Library.startWorking();

        book = new Book(
            "dfbsbzdbb",
            "arbdabadba",
            "abgaagrg",
            1214,
            2,
            2,
            2
        );
    }

    @AfterAll
    static void endWorkingLibrary() {
        Library.endWorking();
        Library.setIsFirstDayWorking(true);
        Library.getVisitors().clear();
        Library.getBlackListOfVisitors().clear();
        Library.getBusyBooks().clear();
        Library.getVisitorsAtLibrary().clear();
        Library.getLibraryBookcase().clear();
        Library.getBooks().clear();
    }

    @Test
    void testCloseAndOpenLibrary() {
        manager.closeLibrary();
        assertFalse(Library.isOpened());
        manager.openLibrary();
        assertTrue(Library.isOpened());
    }

    @Test
    void testThrowException() {
        assertThrows(
            NullPointerException.class,
            () -> manager.add(null)
        );
        assertThrows(
            NullPointerException.class,
            () -> manager.remove(null)
        );
        assertThrows(
            NoSuchBookException.class,
            () -> manager.remove(book)
        );
    }

    @Test
    void testWorkingAddAndRemove() {
        //проверка на добавление
        int sizeBeforeAdd = Library
            .getBooks()
            .size();

        manager.add(book);

        int sizeAfterAdd = Library
            .getBooks()
            .size();

        assertEquals(
            sizeAfterAdd - 1,
            sizeBeforeAdd);

        assertTrue(Library
            .getBooks()
            .contains(book));

        assertTrue(Library
            .getLibraryBookcase()
            .get(book.getBookcaseNumber())
            .getShelfInBookcase()
            .get(book.getShelfNumber())
            .getBookShelf()
            .containsValue(book));

        //проверка на удаление
        sizeBeforeAdd = Library
            .getBooks()
            .size();

        manager.remove(book);

        sizeAfterAdd = Library
            .getBooks()
            .size();

        assertEquals(
            sizeAfterAdd + 1,
            sizeBeforeAdd);

        assertFalse(
            Library
                .getBooks()
                .contains(book)
        );

        assertFalse(Library
            .getLibraryBookcase()
            .get(book.getBookcaseNumber())
            .getShelfInBookcase()
            .get(book.getShelfNumber())
            .getBookShelf()
            .containsValue(book));
    }
}
