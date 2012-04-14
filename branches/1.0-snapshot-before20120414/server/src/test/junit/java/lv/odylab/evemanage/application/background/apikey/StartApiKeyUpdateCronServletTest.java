package lv.odylab.evemanage.application.background.apikey;

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
public class StartApiKeyUpdateCronServletTest {
    @Mock
    private UpdateApiKeyTaskLauncher updateApiKeyTaskLauncher;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    private StartApiKeyUpdateCronServlet startApiKeyUpdateCronServlet;

    @Before
    public void setUp() {
        startApiKeyUpdateCronServlet = new StartApiKeyUpdateCronServlet(updateApiKeyTaskLauncher);
    }

    @Test
    public void testDoGet() throws IOException, ServletException {
        startApiKeyUpdateCronServlet.doGet(httpServletRequest, httpServletResponse);
        verify(updateApiKeyTaskLauncher).launchForAll();
    }
}
