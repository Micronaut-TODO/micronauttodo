package io.micronaut.bootstrap;

/**
 * @see <a href="https://getbootstrap.com/docs/5.3/components/alerts/">Alerts</a>
 */
public enum AlertVariant {
    PRIMARY,
    SECONDARY,
    SUCCESS,
    DANGER,
    WARNING,
    INFO,
    LIGHT,
    DARK;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}