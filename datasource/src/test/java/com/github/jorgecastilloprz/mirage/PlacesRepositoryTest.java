package com.github.jorgecastilloprz.mirage;

import com.github.jorgecastilloprz.mirage.di.DaggerTestRepositoryComponent;
import com.github.jorgecastilloprz.mirage.di.DataSourceModule;
import com.github.jorgecastilloprz.mirage.di.TestApiModule;
import com.github.jorgecastilloprz.mirage.di.TestRepositoryComponent;
import com.jorgecastilloprz.mirage.di.RepositoryModule;
import com.jorgecastilloprz.mirage.interactor.exceptions.ObtainPlacesException;
import com.jorgecastilloprz.mirage.model.Place;
import com.jorgecastilloprz.mirage.repository.PlacesRepository;
import com.squareup.okhttp.mockwebserver.MockResponse;
import java.util.List;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author jorge
 * @since 24/07/15
 */
public class PlacesRepositoryTest {

  private TestApiModule apiModule;
  private MockResponse oneVenueResponse;

  @Inject PlacesRepository placesRepository;

  @Before public void setup() {
    apiModule = new TestApiModule();
    TestRepositoryComponent component = DaggerTestRepositoryComponent.builder()
        .repositoryModule(new RepositoryModule())
        .dataSourceModule(new DataSourceModule())
        .testApiModule(apiModule)
        .build();

    component.inject(this);

    oneVenueResponse = new MockResponse().setResponseCode(200)
        .setBody("{\"meta\":{\"code\":200},\"notifications\":[{\"type\":\"notificationTray\","
            + "\"item\":{\"unreadCount\":0}}],\"response\":{\"suggestedFilters\":"
            + "{\"header\":\"Tap to show:\",\"filters\":[{\"name\":\"Open now\",\""
            + "key\":\"openNow\"}]},\"warning\":{\"text\":\"There aren't a lot of results "
            + "near you. Try something more general, reset your filters, or expand the "
            + "search area.\"},\"suggestedRadius\":1305,\"headerLocation\":\"La Flota\","
            + "\"headerFullLocation\":\"La Flota, Murcia\",\"headerLocationGranularity\""
            + ":\"neighborhood\",\"query\":\"outdoors recreation\",\"totalResults\":39,\""
            + "suggestedBounds\":{\"ne\":{\"lat\":37.98859029760692,\"lng\":"
            + "-1.1277595922524786},\"sw\":{\"lat\":37.98589063782147,\"lng\":"
            + "-1.1305753240450749}},\"groups\":[{\"type\":\"Recommended Places\","
            + "\"name\":\"recommended\",\"items\":[{\"reasons\":{\"count\":0,\"items\""
            + ":[{\"summary\":\"This spot is popular\",\"type\":\"general\",\"reasonName\""
            + ":\"globalInteractionReason\"}]},\"venue\":{\"id\":\"4be99811a9900f4794811540"
            + "\",\"name\":\"Plaza Santo Domingo\",\"contact\":{},\"location\":{\"address\""
            + ":\"Pl. Santo Domingo\",\"lat\":37.987240467714194,\"lng\":-1.1291674581487767"
            + ",\"distance\":884,\"postalCode\":\"30001\",\"cc\":\"ES\",\"city\":\"Murcia\""
            + ",\"state\":\"Murcia\",\"country\":\"Spain\",\"formattedAddress\":[\""
            + "Pl. Santo Domingo\",\"30001 Murcia Murcia\",\"Spain\"]},\"categories\""
            + ":[{\"id\":\"4bf58dd8d48988d164941735\",\"name\":\"Plaza\",\"pluralName\""
            + ":\"Plazas\",\"shortName\":\"Plaza\",\"icon\":{\"prefix\":\""
            + "https:\\/\\/ss3.4sqi.net\\/img\\/categories_v2\\/parks_outdoors\\/plaza_\",\""
            + "suffix\":\".png\"},\"primary\":true}],\"verified\":false,\"stats\":{\""
            + "checkinsCount\":3261,\"usersCount\":957,\"tipCount\":20},\"url\":\""
            + "http:\\/\\/es.wikipedia.org\\/wiki\\/Plaza_de_Santo_Domingo_(Murcia)"
            + "\",\"like\":false,\"specials\":{\"count\":0,\"items\":[]},\"photos\""
            + ":{\"count\":168,\"groups\":[]},\"hereNow\":{\"count\":1,\"summary\":\""
            + "One other person is here\",\"groups\":[{\"type\":\"others\",\"name\":\""
            + "Other people here\",\"count\":1,\"items\":[]}]}},\"tips\":[{\"id\":\""
            + "4e5cfcc51495d25a3a02f87e\",\"createdAt\":1314716869,\"text\":\""
            + "Hace un siglo era la plaza del mercado, hoy es un habitual punto de encuentro"
            + " y quedada para los Murcianos. El ficus tiene más de 100 años. Muchos sitios"
            + " con terracita para un café o cenita\",\"type\":\"user\",\"canonicalUrl\":\""
            + "https:\\/\\/foursquare.com\\/item\\/4e5cfcc51495d25a3a02f87e\",\"photo\":{\""
            + "id\":\"4e5cfcccfa76a4cf148cb0fe\",\"createdAt\":1314716876,\"source\":{\""
            + "name\":\"Foursquare for iOS\",\"url\":\"https:\\/\\/foursquare.com\\/download"
            + "\\/#\\/iphone\"},\"prefix\":\"https:\\/\\/irs0.4sqi.net\\/img\\/general\\/\","
            + "\"suffix\":\"\\/W4R0TN42WGDOAEZTQNB2LVJLXYFHILGFKCDEOFLBHUZY22S0.jpg\",\""
            + "width\":720,\"height\":537},\"photourl\":\"https:\\/\\/irs0.4sqi.net\\/img\\/"
            + "general\\/original\\/W4R0TN42WGDOAEZTQNB2LVJLXYFHILGFKCDEOFLBHUZY22S0.jpg\""
            + ",\"likes\":{\"count\":9,\"groups\":[],\"summary\":\"9 likes\"},\"logView\":"
            + "true,\"todo\":{\"count\":1},\"user\":{\"id\":\"12446050\",\"firstName\":\""
            + "Leles\",\"gender\":\"female\",\"photo\":{\"prefix\":\"https:\\/\\/"
            + "irs2.4sqi.net\\/img\\/user\\/\",\"suffix\":\"\\/3Z5BTEJR3NLWCBDU.jpg\"}}}],"
            + "\"referralId\":\"e-0-4be99811a9900f4794811540-0\"}]}]}}");
  }

  @Test public void shouldReturnOnePlace() throws ObtainPlacesException {
    apiModule.getMockWebServer().enqueue(oneVenueResponse);
    List<Place> placesAround =
        placesRepository.obtainPlacesAround(0, 37.992360, -1.121461, 200, 100000);

    assertEquals(1, placesAround.size());
  }

  @Test public void shouldReturnPlazaSantoDomingo() throws ObtainPlacesException {
    apiModule.getMockWebServer().enqueue(oneVenueResponse);
    List<Place> placesAround =
        placesRepository.obtainPlacesAround(0, 37.992360, -1.121461, 200, 100000);

    assertEquals("4be99811a9900f4794811540", placesAround.get(0).getId());
  }

  @Test(expected = ObtainPlacesException.class) public void shouldThrowAnObtainedPlacesException()
      throws ObtainPlacesException {

    MockResponse mockBadFormatResponse = new MockResponse();
    mockBadFormatResponse.setBody("_-.%%&$ *`");
    apiModule.getMockWebServer().enqueue(mockBadFormatResponse);
    List<Place> placesAround =
        placesRepository.obtainPlacesAround(0, 37.992360, -1.121461, 200, 100000);
  }
}
