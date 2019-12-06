package com.mti.locationtracker.Model;

public class User {
    private String _name_;
    private String _phone_;

    public User(String _name_, String _phone_)
    {
        this.set_name_(_name_);
        this.set_phone_(_phone_);
    }

    public String get_name_() {
        return _name_;
    }

    public void set_name_(String _name_) {
        this._name_ = _name_;
    }

    public String get_phone_() {
        return _phone_;
    }

    public void set_phone_(String _phone_) {
        this._phone_ = _phone_;
    }
}
