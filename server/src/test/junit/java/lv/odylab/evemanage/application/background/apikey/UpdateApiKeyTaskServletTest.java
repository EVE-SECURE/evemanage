package lv.odylab.evemanage.application.background.apikey;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.EveApiException;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.service.eve.EveUpdateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateApiKeyTaskServletTest {
    @Mock
    private EveUpdateService eveUpdateService;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    private UpdateApiKeyTaskServlet updateApiKeyTaskServlet;

    @Before
    public void setUp() {
        updateApiKeyTaskServlet = new UpdateApiKeyTaskServlet(eveUpdateService);
    }

    @Test
    public void testDoGet() throws Exception {
        when(httpServletRequest.getParameter("userID")).thenReturn("1");
        updateApiKeyTaskServlet.doGet(httpServletRequest, httpServletResponse);
        verify(eveUpdateService, times(1)).updateApiKeysForUser(new Key<User>(User.class, 1));
    }

    @Test
    public void testDoPost() throws Exception {
        when(httpServletRequest.getParameter("userID")).thenReturn("1");
        updateApiKeyTaskServlet.doPost(httpServletRequest, httpServletResponse);
        verify(eveUpdateService, times(1)).updateApiKeysForUser(new Key<User>(User.class, 1));
    }

    @Test
    public void testDoPost_Exception() throws Exception {
        when(httpServletRequest.getParameter("userID")).thenReturn("1");
        doThrow(new EveApiException("Api error message")).when(eveUpdateService).updateApiKeysForUser(new Key<User>(User.class, 1));
        updateApiKeyTaskServlet.doPost(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testDoPost_Throwable() throws Exception {
        updateApiKeyTaskServlet.doPost(httpServletRequest, httpServletResponse);
    }
}
