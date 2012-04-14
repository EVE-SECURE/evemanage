package lv.odylab.evemanage.application.background.consistency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StartConsistencyCheckServletTest {
    @Mock
    private CheckBlueprintTaskLauncher checkBlueprintTaskLauncher;
    @Mock
    private CheckPriceSetTaskLauncher checkPriceSetTaskLauncher;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    private StartConsistencyCheckServlet startConsistencyCheckServlet;

    @Before
    public void setUp() {
        startConsistencyCheckServlet = new StartConsistencyCheckServlet(checkBlueprintTaskLauncher, checkPriceSetTaskLauncher);
    }

    @Test
    public void testDoGet() throws IOException, ServletException {
        startConsistencyCheckServlet.doGet(httpServletRequest, httpServletResponse);
        verify(checkBlueprintTaskLauncher).launchForAll();
        verify(checkPriceSetTaskLauncher).launchForAll();
    }
}
