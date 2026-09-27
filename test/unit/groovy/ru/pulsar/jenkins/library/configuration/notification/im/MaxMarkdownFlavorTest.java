package ru.pulsar.jenkins.library.configuration.notification.im;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MaxMarkdownFlavorTest {

    private final MaxMarkdownFlavor flavor = new MaxMarkdownFlavor();

    @Test
    void escape_returns_null_for_null() {
        assertThat(flavor.escape(null)).isNull();
    }

    @Test
    void escape_leaves_plain_text_untouched() {
        assertThat(flavor.escape("Сборка успешно завершена")).isEqualTo("Сборка успешно завершена");
    }

    @Test
    void escape_escapes_backslash_first() {
        assertThat(flavor.escape("C:\\temp")).isEqualTo("C:\\\\temp");
    }

    @Test
    void escape_escapes_max_markup() {
        assertThat(flavor.escape("C++ ~/path x^2 #42 > note ~~a~~ ++b++ ^^c^^"))
            .isEqualTo("C\\+\\+ \\~/path x\\^2 \\#42 \\> note \\~\\~a\\~\\~ \\+\\+b\\+\\+ \\^\\^c\\^\\^");
        assertThat(flavor.escape("*bold* _italic_ `code`")).isEqualTo("\\*bold\\* \\_italic\\_ \\`code\\`");
        assertThat(flavor.escape("[text](url)")).isEqualTo("\\[text\\]\\(url\\)");
    }

    @Test
    void escape_does_not_escape_characters_outside_max_markup() {
        assertThat(flavor.escape("1.0-rc! a|b <x> a=b")).isEqualTo("1.0-rc! a|b <x> a=b");
    }

    @Test
    void link_escapes_text_and_url() {
        assertThat(flavor.link("build #42", "https://ci/job(1)/42/"))
            .isEqualTo("[build \\#42](https://ci/job(1\\)/42/)");
    }

    @Test
    void link_without_url_returns_escaped_text() {
        assertThat(flavor.link("build #42", null)).isEqualTo("build \\#42");
    }
}
