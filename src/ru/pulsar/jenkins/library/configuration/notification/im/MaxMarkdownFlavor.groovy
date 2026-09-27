package ru.pulsar.jenkins.library.configuration.notification.im

import com.cloudbees.groovy.cps.NonCPS

/**
 * Разметка markdown мессенджера MAX.
 *
 * Экранируются символы, которые MAX трактует как разметку, включая его расширения
 * {@code ~~a~~}, {@code ++b++}, {@code ^^c^^}. Набор проверен на реальных сообщениях MAX,
 * поэтому он отличается от {@link StandardMarkdownFlavor} и не должен с ним объединяться.
 */
class MaxMarkdownFlavor implements MarkdownFlavor {

    @Override
    @NonCPS
    String escape(String text) {
        if (text == null) {
            return null
        }
        return text
            .replace('\\', '\\\\')
            .replace('_', '\\_')
            .replace('*', '\\*')
            .replace('`', '\\`')
            .replace('[', '\\[')
            .replace(']', '\\]')
            .replace('(', '\\(')
            .replace(')', '\\)')
            .replace('~', '\\~')
            .replace('+', '\\+')
            .replace('^', '\\^')
            .replace('#', '\\#')
            .replace('>', '\\>')
    }

    @Override
    @NonCPS
    String bullet() {
        return '*'
    }

    @Override
    @NonCPS
    String hash() {
        return '#'
    }

    @Override
    @NonCPS
    String openParen() {
        return '('
    }

    @Override
    @NonCPS
    String closeParen() {
        return ')'
    }

    @Override
    @NonCPS
    String link(String text, String url) {
        if (url == null) {
            return escape(text)
        }
        return "[${escape(text)}](${escapeUrl(url)})"
    }

    @NonCPS
    private static String escapeUrl(String url) {
        if (url == null) {
            return null
        }
        return url
            .replace('\\', '\\\\')
            .replace(')', '\\)')
    }
}
