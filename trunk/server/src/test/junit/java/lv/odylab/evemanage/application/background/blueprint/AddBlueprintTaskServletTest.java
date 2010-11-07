package lv.odylab.evemanage.application.background.blueprint;

import com.googlecode.objectify.Key;
import lv.odylab.evemanage.application.exception.EveDbException;
import lv.odylab.evemanage.domain.user.User;
import lv.odylab.evemanage.service.blueprint.BlueprintManagementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddBlueprintTaskServletTest {
    @Mock
    private BlueprintManagementService blueprintManagementService;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    private AddBlueprintTaskServlet addBlueprintTaskServlet;

    @Before
    public void setUp() {
        addBlueprintTaskServlet = new AddBlueprintTaskServlet(blueprintManagementService);
    }

    @Test
    public void testDoPost() throws Exception {
        when(httpServletRequest.getParameter("userID")).thenReturn("1");
        when(httpServletRequest.getParameter("blueprintTypeID")).thenReturn("2");
        when(httpServletRequest.getParameter("itemID")).thenReturn("3");
        when(httpServletRequest.getParameter("meLevel")).thenReturn("4");
        when(httpServletRequest.getParameter("peLevel")).thenReturn("5");
        when(httpServletRequest.getParameter("attachedCharacterID")).thenReturn("6");
        when(httpServletRequest.getParameter("sharingLevel")).thenReturn("PERSONAL");
        addBlueprintTaskServlet.doPost(httpServletRequest, httpServletResponse);
        verify(blueprintManagementService).createBlueprint(2L, 3L, 4, 5, 6L, "PERSONAL", new Key<User>(User.class, 1));
    }

    @Test
    public void testDoPost_Exception() throws Exception {
        when(httpServletRequest.getParameter("userID")).thenReturn("1");
        when(httpServletRequest.getParameter("blueprintTypeID")).thenReturn("2");
        when(httpServletRequest.getParameter("itemID")).thenReturn("3");
        when(httpServletRequest.getParameter("meLevel")).thenReturn("4");
        when(httpServletRequest.getParameter("peLevel")).thenReturn("5");
        when(httpServletRequest.getParameter("attachedCharacterID")).thenReturn("6");
        when(httpServletRequest.getParameter("sharingLevel")).thenReturn("PERSONAL");
        doThrow(new EveDbException(new RuntimeException())).when(blueprintManagementService).createBlueprint(2L, 3L, 4, 5, 6L, "PERSONAL", new Key<User>(User.class, 1));
        addBlueprintTaskServlet.doPost(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testDoPost_Throwable() throws Exception {
        addBlueprintTaskServlet.doPost(httpServletRequest, httpServletResponse);
    }
}
