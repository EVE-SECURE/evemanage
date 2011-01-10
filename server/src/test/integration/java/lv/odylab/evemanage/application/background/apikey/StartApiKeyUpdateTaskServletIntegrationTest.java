package lv.odylab.evemanage.application.background.apikey;

public class StartApiKeyUpdateTaskServletIntegrationTest {
    // TODO fix this
    /*private final GoogleAppEngineServices googleAppEngineServices = new GoogleAppEngineServices();
    private final EveManageObjectifyFactory objectifyFactory = new EveManageObjectifyFactory();
    private final ApiKeyDao apiKeyDao = new ApiKeyDao(objectifyFactory);
    private final UserDao userDao = new UserDao(objectifyFactory);
    private final StartApiKeyUpdateCronServlet startApiKeyUpdateCronServlet = new StartApiKeyUpdateCronServlet(googleAppEngineServices, userDao, "default");

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(), new LocalTaskQueueTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testManyApiKeys() throws IOException, ServletException {
        for (int i = 0; i < 500; i++) {
            User user = new User();
            userDao.put(user);
            Key<User> userKey = new Key<User>(User.class, user.getId());
            for (int j = 0; j < 10; j++) {
                ApiKey apiKey = new ApiKey();
                apiKey.setUser(userKey);
                apiKeyDao.putWithoutChecks(apiKey);
            }
        }

        startApiKeyUpdateCronServlet.doGet(null, null);
        LocalTaskQueue taskQueue = LocalTaskQueueTestConfig.getLocalTaskQueue();
        assertEquals(500, taskQueue.getQueueStateInfo().get("default").getCountTasks());
    }*/
}
