package games.strategy.engine.framework;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.apache.commons.io.IOCase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import games.strategy.engine.framework.GameDataFileUtils.SaveGameFormat;

public final class GameDataFileUtilsTests {
  @Nested
  public final class AddExtensionTests {
    @Nested
    public final class WhenSaveGameFormatIsSerializationTest {
      private String addExtension(final String fileName) {
        return GameDataFileUtils.addExtension(fileName, SaveGameFormat.SERIALIZATION);
      }

      @Test
      public void shouldAddExtensionWhenExtensionAbsent() {
        assertThat(addExtension("file"), is("file.tsvg"));
      }

      @Test
      public void shouldAddExtensionWhenExtensionPresent() {
        assertThat(addExtension("file.tsvg"), is("file.tsvg.tsvg"));
      }
    }

    @Nested
    public final class WhenSaveGameFormatIsProxySerializationTest {
      private String addExtension(final String fileName) {
        return GameDataFileUtils.addExtension(fileName, SaveGameFormat.PROXY_SERIALIZATION);
      }

      @Test
      public void shouldAddExtensionWhenExtensionAbsent() {
        assertThat(addExtension("file"), is("file.tsvgx"));
      }

      @Test
      public void shouldAddExtensionWhenExtensionPresent() {
        assertThat(addExtension("file.tsvgx"), is("file.tsvgx.tsvgx"));
      }
    }
  }

  @Nested
  public final class AddExtensionIfAbsentTests {
    @Nested
    public final class WhenSaveGameFormatIsSerializationTests {
      @Nested
      public final class WhenFileSystemIsCaseSensitiveTest {
        private String addExtensionIfAbsent(final String fileName) {
          return GameDataFileUtils.addExtensionIfAbsent(fileName, SaveGameFormat.SERIALIZATION, IOCase.SENSITIVE);
        }

        @Test
        public void shouldAddExtensionWhenExtensionAbsent() {
          assertThat(addExtensionIfAbsent("file"), is("file.tsvg"));
        }

        @Test
        public void shouldNotAddExtensionWhenSameCasedExtensionPresent() {
          assertThat(addExtensionIfAbsent("file.tsvg"), is("file.tsvg"));
        }

        @Test
        public void shouldAddExtensionWhenDifferentCasedExtensionPresent() {
          assertThat(addExtensionIfAbsent("file.TSVG"), is("file.TSVG.tsvg"));
        }
      }

      @Nested
      public final class WhenFileSystemIsCaseInsensitiveTest {
        private String addExtensionIfAbsent(final String fileName) {
          return GameDataFileUtils.addExtensionIfAbsent(fileName, SaveGameFormat.SERIALIZATION, IOCase.INSENSITIVE);
        }

        @Test
        public void shouldAddExtensionWhenExtensionAbsent() {
          assertThat(addExtensionIfAbsent("file"), is("file.tsvg"));
        }

        @Test
        public void shouldNotAddExtensionWhenSameCasedExtensionPresent() {
          assertThat(addExtensionIfAbsent("file.tsvg"), is("file.tsvg"));
        }

        @Test
        public void shouldNotAddExtensionWhenDifferentCasedExtensionPresent() {
          assertThat(addExtensionIfAbsent("file.TSVG"), is("file.TSVG"));
        }
      }
    }

    @Nested
    public final class WhenSaveGameFormatIsProxySerializationTests {
      @Nested
      public final class WhenFileSystemIsCaseSensitiveTest {
        private String addExtensionIfAbsent(final String fileName) {
          return GameDataFileUtils.addExtensionIfAbsent(fileName, SaveGameFormat.PROXY_SERIALIZATION, IOCase.SENSITIVE);
        }

        @Test
        public void shouldAddExtensionWhenExtensionAbsent() {
          assertThat(addExtensionIfAbsent("file"), is("file.tsvgx"));
        }

        @Test
        public void shouldNotAddExtensionWhenSameCasedExtensionPresent() {
          assertThat(addExtensionIfAbsent("file.tsvgx"), is("file.tsvgx"));
        }

        @Test
        public void shouldAddExtensionWhenDifferentCasedExtensionPresent() {
          assertThat(addExtensionIfAbsent("file.TSVGX"), is("file.TSVGX.tsvgx"));
        }
      }

      @Nested
      public final class WhenFileSystemIsCaseInsensitiveTest {
        private String addExtensionIfAbsent(final String fileName) {
          return GameDataFileUtils.addExtensionIfAbsent(
              fileName,
              SaveGameFormat.PROXY_SERIALIZATION,
              IOCase.INSENSITIVE);
        }

        @Test
        public void shouldAddExtensionWhenExtensionAbsent() {
          assertThat(addExtensionIfAbsent("file"), is("file.tsvgx"));
        }

        @Test
        public void shouldNotAddExtensionWhenSameCasedExtensionPresent() {
          assertThat(addExtensionIfAbsent("file.tsvgx"), is("file.tsvgx"));
        }

        @Test
        public void shouldNotAddExtensionWhenDifferentCasedExtensionPresent() {
          assertThat(addExtensionIfAbsent("file.TSVGX"), is("file.TSVGX"));
        }
      }
    }
  }

  @Nested
  public final class IsCandidateFileNameTests {
    @Nested
    public final class WhenSaveGameFormatIsSerializationTests {
      @Nested
      public final class WhenFileSystemIsCaseSensitiveTest {
        private boolean isCandidateFileName(final String fileName) {
          return GameDataFileUtils.isCandidateFileName(fileName, SaveGameFormat.SERIALIZATION, IOCase.SENSITIVE);
        }

        @Test
        public void shouldReturnFalseWhenExtensionAbsent() {
          assertThat(isCandidateFileName("file"), is(false));
        }

        @Test
        public void shouldReturnTrueWhenSameCasedPrimaryExtensionPresent() {
          assertThat(isCandidateFileName("file.tsvg"), is(true));
        }

        @Test
        public void shouldReturnFalseWhenDifferentCasedPrimaryExtensionPresent() {
          assertThat(isCandidateFileName("file.TSVG"), is(false));
        }

        @Test
        public void shouldReturnTrueWhenSameCasedLegacyExtensionPresent() {
          assertThat(isCandidateFileName("file.svg"), is(true));
        }

        @Test
        public void shouldReturnFalseWhenDifferentCasedLegacyExtensionPresent() {
          assertThat(isCandidateFileName("file.SVG"), is(false));
        }

        @Test
        public void shouldReturnTrueWhenSameCasedMacOsAlternativeExtensionPresent() {
          assertThat(isCandidateFileName("filetsvg.gz"), is(true));
        }

        @Test
        public void shouldReturnFalseWhenDifferentCasedMacOsAlternativeExtensionPresent() {
          assertThat(isCandidateFileName("fileTSVG.GZ"), is(false));
        }
      }

      @Nested
      public final class WhenFileSystemIsCaseInsensitiveTest {
        private boolean isCandidateFileName(final String fileName) {
          return GameDataFileUtils.isCandidateFileName(fileName, SaveGameFormat.SERIALIZATION, IOCase.INSENSITIVE);
        }

        @Test
        public void shouldReturnFalseWhenExtensionAbsent() {
          assertThat(isCandidateFileName("file"), is(false));
        }

        @Test
        public void shouldReturnTrueWhenSameCasedPrimaryExtensionPresent() {
          assertThat(isCandidateFileName("file.tsvg"), is(true));
        }

        @Test
        public void shouldReturnTrueWhenDifferentCasedPrimaryExtensionPresent() {
          assertThat(isCandidateFileName("file.TSVG"), is(true));
        }

        @Test
        public void shouldReturnTrueWhenSameCasedLegacyExtensionPresent() {
          assertThat(isCandidateFileName("file.svg"), is(true));
        }

        @Test
        public void shouldReturnTrueWhenDifferentCasedLegacyExtensionPresent() {
          assertThat(isCandidateFileName("file.SVG"), is(true));
        }

        @Test
        public void shouldReturnTrueWhenSameCasedMacOsAlternativeExtensionPresent() {
          assertThat(isCandidateFileName("filetsvg.gz"), is(true));
        }

        @Test
        public void shouldReturnTrueWhenDifferentCasedMacOsAlternativeExtensionPresent() {
          assertThat(isCandidateFileName("fileTSVG.GZ"), is(true));
        }
      }
    }

    @Nested
    public final class WhenSaveGameFormatIsProxySerializationTests {
      @Nested
      public final class WhenFileSystemIsCaseSensitiveTest {
        private boolean isCandidateFileName(final String fileName) {
          return GameDataFileUtils.isCandidateFileName(fileName, SaveGameFormat.PROXY_SERIALIZATION, IOCase.SENSITIVE);
        }

        @Test
        public void shouldReturnFalseWhenExtensionAbsent() {
          assertThat(isCandidateFileName("file"), is(false));
        }

        @Test
        public void shouldReturnTrueWhenSameCasedExtensionPresent() {
          assertThat(isCandidateFileName("file.tsvgx"), is(true));
        }

        @Test
        public void shouldReturnFalseWhenDifferentCasedExtensionPresent() {
          assertThat(isCandidateFileName("file.TSVGX"), is(false));
        }
      }

      @Nested
      public final class WhenFileSystemIsCaseInsensitiveTest {
        private boolean isCandidateFileName(final String fileName) {
          return GameDataFileUtils.isCandidateFileName(
              fileName,
              SaveGameFormat.PROXY_SERIALIZATION,
              IOCase.INSENSITIVE);
        }

        @Test
        public void shouldReturnFalseWhenExtensionAbsent() {
          assertThat(isCandidateFileName("file"), is(false));
        }

        @Test
        public void shouldReturnTrueWhenSameCasedExtensionPresent() {
          assertThat(isCandidateFileName("file.tsvgx"), is(true));
        }

        @Test
        public void shouldReturnTrueWhenDifferentCasedExtensionPresent() {
          assertThat(isCandidateFileName("file.TSVGX"), is(true));
        }
      }
    }
  }
}
