package de.codecentric.blogbuster;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class AwesomenessRatingWritable implements Writable {

    private Text firstName;
    private Text lastName;
    private Text country;
    private Text city;
    private Text company;
    private LongWritable rating;

    public AwesomenessRatingWritable() {
        firstName = new Text();
        lastName = new Text();
        country = new Text();
        city = new Text();
        company = new Text();
        rating = new LongWritable();
    }

    public boolean isUserInformation() {
        return firstName.toString().length() > 0 || lastName.toString().length() > 0 || country.toString().length() > 0 || city.toString().length() > 0 || company.toString().length() > 0;
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        firstName.readFields(input);
        lastName.readFields(input);
        country.readFields(input);
        city.readFields(input);
        company.readFields(input);
        rating.readFields(input);
    }

    @Override
    public void write(DataOutput output) throws IOException {
        firstName.write(output);
        lastName.write(output);
        country.write(output);
        city.write(output);
        company.write(output);
        rating.write(output);
    }

    public Text getFirstName() {
        return firstName;
    }

    public void setFirstName(Text name) {
        this.firstName = name;
    }

    public Text getLastName() {
        return lastName;
    }

    public void setLastName(Text lastName) {
        this.lastName = lastName;
    }

    public Text getCountry() {
        return country;
    }

    public void setCountry(Text country) {
        this.country = country;
    }

    public Text getCity() {
        return city;
    }

    public void setCity(Text city) {
        this.city = city;
    }

    public Text getCompany() {
        return company;
    }

    public void setCompany(Text company) {
        this.company = company;
    }

    public LongWritable getRating() {
        return rating;
    }

    public void setRating(LongWritable rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((rating == null) ? 0 : rating.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AwesomenessRatingWritable other = (AwesomenessRatingWritable) obj;
        if (city == null) {
            if (other.city != null) {
                return false;
            }
        } else if (!city.equals(other.city)) {
            return false;
        }
        if (company == null) {
            if (other.company != null) {
                return false;
            }
        } else if (!company.equals(other.company)) {
            return false;
        }
        if (country == null) {
            if (other.country != null) {
                return false;
            }
        } else if (!country.equals(other.country)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (rating == null) {
            if (other.rating != null) {
                return false;
            }
        } else if (!rating.equals(other.rating)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AwesomenessRatingWritable [name=" + firstName + ", lastName=" + lastName + ", country=" + country + ", city=" + city + ", company=" + company + ", rating=" + rating + "]";
    }

}
