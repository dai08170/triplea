package games.strategy.triplea.ui.screen.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.List;

import games.strategy.engine.data.GameData;
import games.strategy.engine.data.Territory;
import games.strategy.triplea.ui.mapdata.MapData;

public class SeaZoneOutlineDrawable implements IDrawable {
  private final String territoryName;

  public SeaZoneOutlineDrawable(final String territoryName) {
    this.territoryName = territoryName;
  }

  @Override
  public void draw(final Rectangle bounds, final GameData data, final Graphics2D graphics, final MapData mapData) {
    final Territory territory = data.getMap().getTerritory(territoryName);
    final List<Polygon> polys = mapData.getPolygons(territory);
    for (Polygon polygon : polys) {
      // if we dont have to draw, dont
      if (!polygon.intersects(bounds) && !polygon.contains(bounds)) {
        continue;
      }
      // use a copy since we will move the polygon
      polygon = new Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
      polygon.translate(-bounds.x, -bounds.y);
      graphics.setColor(Color.BLACK);
      graphics.drawPolygon(polygon);
    }
  }

  @Override
  public int getLevel() {
    return POLYGONS_LEVEL;
  }
}
