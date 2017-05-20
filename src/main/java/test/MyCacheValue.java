package test;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 *
 */
public class MyCacheValue implements Serializable {

    private String episodeDescription;

    public MyCacheValue() {
    }

    public MyCacheValue(String episodeDescription) {
        this.episodeDescription = episodeDescription;
    }

    public String getEpisodeDescription() {
        return episodeDescription;
    }

    public void setEpisodeDescription(String episodeDescription) {
        this.episodeDescription = episodeDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MyCacheValue that = (MyCacheValue) o;

        return new EqualsBuilder()
                .append(episodeDescription, that.episodeDescription)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(episodeDescription)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("episodeDescription", episodeDescription)
                .toString();
    }
}
