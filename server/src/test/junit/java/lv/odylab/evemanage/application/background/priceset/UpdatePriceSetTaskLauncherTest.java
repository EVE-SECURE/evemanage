package lv.odylab.evemanage.application.background.priceset;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetDao;
import lv.odylab.evemanage.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdatePriceSetTaskLauncherTest {
    @Mock
    private GoogleAppEngineServices googleAppEngineServices;
    @Mock
    private PriceSetDao priceSetDao;
    @Mock
    private Queue queue;
    private UpdatePriceSetTaskLauncher updatePriceSetTaskLauncher;

    @Before
    public void setUp() {
        updatePriceSetTaskLauncher = new UpdatePriceSetTaskLauncher(googleAppEngineServices, priceSetDao, "default");
    }

    @Test
    public void testLaunchForDelete() {
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        List<PriceSet> priceSets = new ArrayList<PriceSet>();
        PriceSet priceSet1 = new PriceSet();
        PriceSet priceSet2 = new PriceSet();
        priceSets.add(priceSet1);
        priceSets.add(priceSet2);
        when(priceSetDao.getAll(new Key<User>(User.class, 1L), 2L)).thenReturn(priceSets);
        updatePriceSetTaskLauncher.launchForDelete(1L, 2L);
        verify(queue, times(2)).add(any(TaskOptions.class));
    }

    @Test
    public void testLaunchForDetach() {
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        lv.odylab.evemanage.domain.eve.Character character = new Character();
        character.setCharacterID(1L);
        character.setName("characterName");
        List<PriceSet> priceSets = new ArrayList<PriceSet>();
        PriceSet priceSet1 = new PriceSet();
        priceSet1.setId(2L);
        PriceSet priceSet2 = new PriceSet();
        priceSet2.setId(3L);
        priceSets.add(priceSet1);
        priceSets.add(priceSet2);
        when(priceSetDao.getAll(new Key<User>(User.class, 4L), 1L)).thenReturn(priceSets);
        updatePriceSetTaskLauncher.launchForDetach(4L, character);
        verify(queue, times(2)).add(any(TaskOptions.class));
    }

    @Test
    public void testLaunchForUpdate() {
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        Character character = new Character();
        character.setCharacterID(1L);
        character.setName("characterName");
        character.setCorporationID(2L);
        character.setCorporationName("corporationName");
        character.setCorporationTicker("corporationTicker");
        character.setAllianceID(3L);
        character.setAllianceName("allianceName");
        List<PriceSet> priceSets = new ArrayList<PriceSet>();
        PriceSet priceSet1 = new PriceSet();
        priceSet1.setId(4L);
        PriceSet priceSet2 = new PriceSet();
        priceSet2.setId(5L);
        priceSets.add(priceSet1);
        priceSets.add(priceSet2);
        when(priceSetDao.getAll(new Key<User>(User.class, 4L), 1L)).thenReturn(priceSets);
        updatePriceSetTaskLauncher.launchForUpdate(4L, character);
        verify(queue, times(2)).add(any(TaskOptions.class));
    }

    @Test
    public void testLaunchForUpdate_NoCorporation() {
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        Character character = new Character();
        character.setCharacterID(1L);
        character.setName("characterName");
        List<PriceSet> priceSets = new ArrayList<PriceSet>();
        PriceSet priceSet1 = new PriceSet();
        priceSet1.setId(4L);
        PriceSet priceSet2 = new PriceSet();
        priceSet2.setId(5L);
        priceSets.add(priceSet1);
        priceSets.add(priceSet2);
        when(priceSetDao.getAll(new Key<User>(User.class, 4L), 1L)).thenReturn(priceSets);
        updatePriceSetTaskLauncher.launchForUpdate(4L, character);
        verify(queue, times(2)).add(any(TaskOptions.class));
    }

    @Test
    public void testLaunchForUpdate_NoAlliance() {
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        Character character = new Character();
        character.setCharacterID(1L);
        character.setName("characterName");
        character.setCorporationID(2L);
        character.setCorporationName("corporationName");
        character.setCorporationTicker("corporationTicker");
        List<PriceSet> priceSets = new ArrayList<PriceSet>();
        PriceSet priceSet1 = new PriceSet();
        priceSet1.setId(4L);
        PriceSet priceSet2 = new PriceSet();
        priceSet2.setId(5L);
        priceSets.add(priceSet1);
        priceSets.add(priceSet2);
        when(priceSetDao.getAll(new Key<User>(User.class, 4L), 1L)).thenReturn(priceSets);
        updatePriceSetTaskLauncher.launchForUpdate(4L, character);
        verify(queue, times(2)).add(any(TaskOptions.class));
    }
}
