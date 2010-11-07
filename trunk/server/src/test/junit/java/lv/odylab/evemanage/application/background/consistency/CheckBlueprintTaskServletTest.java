package lv.odylab.evemanage.application.background.consistency;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import lv.odylab.evemanage.domain.blueprint.Blueprint;
import lv.odylab.evemanage.domain.blueprint.BlueprintDao;
import lv.odylab.evemanage.domain.eve.Character;
import lv.odylab.evemanage.domain.eve.CharacterDao;
import lv.odylab.evemanage.domain.user.CharacterInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckBlueprintTaskServletTest {
    @Mock
    private BlueprintDao blueprintDao;
    @Mock
    private CharacterDao characterDao;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private HttpServletResponse httpServletResponse;
    private CheckBlueprintTaskServlet checkBlueprintTaskServlet;

    @Before
    public void setUp() {
        checkBlueprintTaskServlet = new CheckBlueprintTaskServlet(blueprintDao, characterDao);
    }

    @Test
    public void testDoPost() throws Exception {
        Blueprint blueprint = new Blueprint();
        CharacterInfo attachedCharacterInfo = new CharacterInfo();
        attachedCharacterInfo.setId(2L);
        Character character = new Character();
        character.setCharacterID(3L);
        character.setName("characterName");
        character.setCorporationID(4L);
        character.setCorporationName("corporationName");
        character.setCorporationTicker("corporationTicker");
        character.setAllianceID(5L);
        character.setAllianceName("allianceName");
        blueprint.setAttachedCharacterInfo(attachedCharacterInfo);
        when(httpServletRequest.getParameter("blueprintID")).thenReturn("1");
        when(blueprintDao.get(new Key<Blueprint>(Blueprint.class, 1))).thenReturn(blueprint);
        when(characterDao.get(new Key<Character>(Character.class, 2))).thenReturn(character);
        checkBlueprintTaskServlet.doPost(httpServletRequest, httpServletResponse);
        verify(blueprintDao).putWithoutChecks(blueprint);
        assertEquals(Long.valueOf(3), attachedCharacterInfo.getCharacterID());
        assertEquals("characterName", attachedCharacterInfo.getName());
        assertEquals(Long.valueOf(4), attachedCharacterInfo.getCorporationID());
        assertEquals("corporationName", attachedCharacterInfo.getCorporationName());
        assertEquals("corporationTicker", attachedCharacterInfo.getCorporationTicker());
        assertEquals(Long.valueOf(5), attachedCharacterInfo.getAllianceID());
        assertEquals("allianceName", attachedCharacterInfo.getAllianceName());
    }

    @Test
    public void testDoPost_WrongCharacter() throws Exception {
        Blueprint blueprint = new Blueprint();
        CharacterInfo attachedCharacterInfo = new CharacterInfo();
        attachedCharacterInfo.setId(2L);
        blueprint.setAttachedCharacterInfo(attachedCharacterInfo);
        when(httpServletRequest.getParameter("blueprintID")).thenReturn("1");
        when(blueprintDao.get(new Key<Blueprint>(Blueprint.class, 1))).thenReturn(blueprint);
        when(characterDao.get(new Key<Character>(Character.class, 2))).thenThrow(new NotFoundException(null));
        checkBlueprintTaskServlet.doPost(httpServletRequest, httpServletResponse);
        verify(blueprintDao).putWithoutChecks(blueprint);
        assertNull(blueprint.getAttachedCharacterInfo());
    }

    @Test
    public void testDoPost_NoCharacter() throws Exception {
        Blueprint blueprint = new Blueprint();
        when(httpServletRequest.getParameter("blueprintID")).thenReturn("1");
        when(blueprintDao.get(new Key<Blueprint>(Blueprint.class, 1))).thenReturn(blueprint);
        checkBlueprintTaskServlet.doPost(httpServletRequest, httpServletResponse);
        verify(blueprintDao, never()).putWithoutChecks(blueprint);
    }

    @Test
    public void testDoPost_Exception() throws Exception {
        when(httpServletRequest.getParameter("userID")).thenReturn("1");
        doThrow(new RuntimeException()).when(blueprintDao).get(new Key<Blueprint>(Blueprint.class, 1));
        checkBlueprintTaskServlet.doPost(httpServletRequest, httpServletResponse);
    }

    @Test
    public void testDoPost_Throwable() throws Exception {
        checkBlueprintTaskServlet.doPost(httpServletRequest, httpServletResponse);
    }
}
