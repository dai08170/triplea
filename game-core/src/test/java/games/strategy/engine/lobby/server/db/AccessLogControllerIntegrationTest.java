package games.strategy.engine.lobby.server.db;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;

import games.strategy.engine.lobby.server.TestUserUtils;
import games.strategy.engine.lobby.server.User;
import games.strategy.engine.lobby.server.login.UserType;
import games.strategy.test.Integration;

@Integration
public final class AccessLogControllerIntegrationTest {
  private final AccessLogController accessLogController = new AccessLogController();

  @Test
  public void insert_ShouldInsertNewRecord() throws Exception {
    final User user = TestUserUtils.newUser();

    for (final UserType userType : UserType.values()) {
      accessLogController.insert(user, userType);

      thenAccessLogRecordShouldExist(user, userType);
    }
  }

  private static void thenAccessLogRecordShouldExist(final User user, final UserType userType) throws Exception {
    final String sql = "select access_time from access_log where username=? and ip=?::inet and mac=? and registered=?";
    try (Connection conn = Database.getPostgresConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, user.getUsername());
      ps.setString(2, user.getInetAddress().getHostAddress());
      ps.setString(3, user.getHashedMacAddress());
      ps.setBoolean(4, userType == UserType.REGISTERED);
      try (ResultSet rs = ps.executeQuery()) {
        assertThat("record should exist", rs.next(), is(true));
        assertThat("access_time column should have a default value", rs.getTimestamp(1), is(not(nullValue())));
        assertThat(
            "only one record should exist "
                + "(possible aliasing from another test run due to no control over access_time value)",
            rs.next(),
            is(false));
      }
    }
  }
}
