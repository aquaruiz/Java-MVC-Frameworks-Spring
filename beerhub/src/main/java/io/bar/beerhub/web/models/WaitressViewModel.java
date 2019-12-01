package io.bar.beerhub.web.models;

import org.springframework.web.multipart.MultipartFile;

public class WaitressViewModel {
    private String id;
    private String name;
    private MultipartFile image;
    private Double tipsRate;

    public WaitressViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Double getTipsRate() {
        return tipsRate;
    }

    public void setTipsRate(Double tipsRate) {
        this.tipsRate = tipsRate;
    }
}
