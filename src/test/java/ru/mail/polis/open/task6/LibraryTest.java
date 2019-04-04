package ru.mail.polis.open.task6;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LibraryTest {

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
    void testShowWorkingLibrary() {

        //Менеджер приходит с утра на работу и открывает библиотеку
        Library.manager.openLibrary();
        assertTrue(Library.isOpened());

        //Каждое утро в библиотеку приходят 3 посетителя, но вот подошёл новый
        Library.onVisit(
            new VisitorImpl(
                "Kvashnin",
                "Artem",
                "Aleksandrovich",
                'M',
                18,
                20000
            )
        );
        assertEquals(
            4,
            Library.getVisitorsAtLibrary().size()
        );

        //Вышла новая книга, и менеджер решил поплнить библиотеку этой книгой
        Book book = new Book(
            "Вторая жизнь Уве",
            "Бакман Фредерик",
            "Проза",
            384,
            1,
            2,
            3
        );
        Library
            .manager
            .add(book);

        assertEquals(
            11,
            Library
                .getBooks()
                .size()
        );
        assertEquals(
            book,
            Library
                .librarian
                .searchSuchBooks(book.getId())
        );

        /*
        Один из посетителей решил взять себе книгу
        Посетитель и библиотекарь няпрямую взаимодействуют, поэтому, когда посетитель хочет взять книгу,
        он сразу говорит это библиотекарю и тот сразу ищет данную книгу
         */
        Library
            .getVisitorsAtLibrary()
            .get(2)
            .takeBook(
                "Клатбище домашних жывотных",
                "Кинг Стивен"
            );
        assertEquals(
            1,
            Library
                .getVisitorsAtLibrary()
                .get(2)
                .getTakenBooks()
                .size()
        );
        assertEquals(
            new Book(
                "Клатбище домашних жывотных",
                "Кинг Стивен",
                "Фантастика, мистика",
                480,
                3,
                1,
                1
            ),
            Library
                .getVisitorsAtLibrary()
                .get(2)
                .getTakenBooks()
                .get(0)
        );
        assertThrows(
            NoSuchBookException.class,
            () -> Library
                .librarian
                .searchSuchBooks(
                    Library
                        .getVisitorsAtLibrary()
                        .get(2)
                        .getTakenBooks()
                        .get(0)
                        .getId()
                )
        );
        assertEquals(
            10,
            Library
                .getBooks()
                .size()
        );

        //Менеджер попросил библиотекаря отчитаться за взятые книги
        Library.librarian.infoAboutUserSpecificBook();

        //Один из посетителей начитался и решил пойти домой, отдыхать тоже надо
        Library.leaveVisitor(
            Library
                .getVisitorsAtLibrary()
                .get(1)
        );
        assertEquals(
            3,
            Library
                .getVisitorsAtLibrary()
                .size()
        );

        //Время на часах 20:00, пора бы и работу закончить
        Library.manager.closeLibrary();
        assertFalse(Library.isOpened());
        assertEquals(
            0,
            Library
                .getVisitorsAtLibrary()
                .size()
        );

        /*
        Менеджер снова приходит с утра на работу и открывает библиотеку, и так как есть посетители, взявшие книги,
        библиотекарь напомнит им, что книги необходимо вернуть вовремя.
        И как всегда 3 постоянных посетителя уже на месте
         */
        Library.manager.openLibrary();
        assertTrue(Library.isOpened());

        //Менеджер заметил, что одну книгу никто не брал уже несколько лет, и решил убрать её из библиотеки
        Book book1 = Library
            .getBooks()
            .get(5);
        Library.manager.remove(
            Library
                .getBooks()
                .get(5)
        );
        assertEquals(
            9,
            Library
                .getBooks()
                .size()
        );
        assertThrows(
            NoSuchBookException.class,
            () -> Library
                .librarian
                .searchSuchBooks(
                    book1.getName(),
                    book1.getAuthor()
                )
        );

        //Другой из посетителей решил взять себе книги
        Library
            .getVisitorsAtLibrary()
            .get(0)
            .takeBook(
                new String[]{"Мятная сказка", "Смертельная белизна"},
                new String[]{"Полярный Александр", "Гэлбрейт Роберт"}
            );
        assertEquals(
            7,
            Library
                .getBooks()
                .size()
        );
        assertEquals(
            2,
            Library
                .getVisitorsAtLibrary()
                .get(0)
                .getTakenBooks()
                .size()
        );
        assertEquals(
            new Book(
                "Мятная сказка",
                "Полярный Александр",
                "Проза",
                208,
                1,
                1,
                1
            ),
            Library
                .getVisitorsAtLibrary()
                .get(0)
                .getTakenBooks()
                .get(0)
        );
        assertEquals(
            new Book(
                "Смертельная белизна",
                "Гэлбрейт Роберт",
                "Детективы, боевики, триллеры",
                672,
                2,
                1,
                1
            ),
            Library
                .getVisitorsAtLibrary()
                .get(0)
                .getTakenBooks()
                .get(1)
        );

        //читатель, бравший 2 книги, прочитал их наконец-то и решил вернуть
        Library
            .getVisitorsAtLibrary()
            .get(0)
            .giveAway(
                Library
                    .getVisitorsAtLibrary()
                    .get(0)
                    .getTakenBooks()
            );

        assertEquals(
            9,
            Library
                .getBooks()
                .size()
        );
        assertEquals(
            0,
            Library
                .getVisitorsAtLibrary()
                .get(0)
                .getTakenBooks()
                .size()
        );

        //День подходит концу, а значит пора заканчивать работу
        Library.manager.closeLibrary();
        assertFalse(Library.isOpened());
        assertEquals(
            0,
            Library
                .getVisitorsAtLibrary()
                .size()
        );
    }
}
