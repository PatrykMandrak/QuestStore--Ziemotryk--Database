package models;

import databaseAccess.BackpacksDAO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CodecoolerTest {

    @Test
    void getBackpacks_WhenCodecoolersBackpackPresent_BackpackPresentInList() {
        Backpack backpack = mock(Backpack.class);
        when(backpack.getBackpackId()).thenReturn(1);
        List<Backpack> backpacks = Arrays.asList(backpack);
        BackpacksDAO backpacksDAO = mock(BackpacksDAO.class);
        when(backpacksDAO.getAll()).thenReturn(backpacks);
        Codecooler codecooler = new Codecooler(1, 1, 1, "Name", "email", "avatar", 1, backpacksDAO);

        List<Backpack> codecoolerBackpacks = codecooler.getBackpacks();

        assertEquals(1, codecoolerBackpacks.size());
    }

    @Test
    void getBackpacks_WhenCodecoolersBackpackAbsent_EmptyListReturned() {
        Backpack backpack = mock(Backpack.class);
        when(backpack.getBackpackId()).thenReturn(1);
        List<Backpack> backpacks = Arrays.asList(backpack);
        BackpacksDAO backpacksDAO = mock(BackpacksDAO.class);
        when(backpacksDAO.getAll()).thenReturn(backpacks);
        Codecooler codecooler = new Codecooler(1, 1, 2, "Name", "email", "avatar", 1, backpacksDAO);

        List<Backpack> codecoolerBackpacks = codecooler.getBackpacks();

        assertTrue(codecoolerBackpacks.isEmpty());
    }


}