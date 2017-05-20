package test;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 */
public class MyCacheKey implements Serializable {

    private int episodeNumber;
    private int seasonNumber;
    private String episodeName;
    private LocalDateTime firstAired;

    public MyCacheKey() {
    }

    public MyCacheKey(int episodeNumber, int seasonNumber, String episodeName, LocalDateTime firstAired) {
        this.episodeNumber = episodeNumber;
        this.seasonNumber = seasonNumber;
        this.episodeName = episodeName;
        this.firstAired = firstAired;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public LocalDateTime getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(LocalDateTime firstAired) {
        this.firstAired = firstAired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MyCacheKey that = (MyCacheKey) o;

        return new EqualsBuilder()
                .append(episodeNumber, that.episodeNumber)
                .append(seasonNumber, that.seasonNumber)
                .append(episodeName, that.episodeName)
                .append(firstAired, that.firstAired)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(episodeNumber)
                .append(seasonNumber)
                .append(episodeName)
                .append(firstAired)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("episodeNumber", episodeNumber)
                .append("seasonNumber", seasonNumber)
                .append("episodeName", episodeName)
                .append("firstAired", firstAired)
                .toString();
    }
}
