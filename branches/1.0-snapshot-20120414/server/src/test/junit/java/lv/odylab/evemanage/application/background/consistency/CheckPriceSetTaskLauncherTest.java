package lv.odylab.evemanage.application.background.consistency;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.googlecode.objectify.Key;
import lv.odylab.appengine.GoogleAppEngineServices;
import lv.odylab.evemanage.domain.priceset.PriceSet;
import lv.odylab.evemanage.domain.priceset.PriceSetDao;
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
public class CheckPriceSetTaskLauncherTest {
    @Mock
    private GoogleAppEngineServices googleAppEngineServices;
    @Mock
    private PriceSetDao priceSetDao;
    @Mock
    private Queue queue;
    private CheckPriceSetTaskLauncher checkPriceSetTaskLauncher;

    @Before
    public void setUp() {
        checkPriceSetTaskLauncher = new CheckPriceSetTaskLauncher(googleAppEngineServices, priceSetDao, "default");
    }

    @Test
    public void testLaunchForAll() {
        List<Key<PriceSet>> priceSetKeys = new ArrayList<Key<PriceSet>>();
        for (int i = 0; i < 10; i++) {
            priceSetKeys.add(new Key<PriceSet>(PriceSet.class, i));
        }
        when(googleAppEngineServices.getQueue("default")).thenReturn(queue);
        when(priceSetDao.getAllKeys()).thenReturn(priceSetKeys);
        checkPriceSetTaskLauncher.launchForAll();
        verify(queue, times(10)).add(any(TaskOptions.class));
    }
}
