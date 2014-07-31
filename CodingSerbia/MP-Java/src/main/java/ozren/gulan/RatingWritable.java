package ozren.gulan;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class RatingWritable implements Writable {

    private Text firstName;
    private Text lastName;
    private Text country;
    private Text city;
    private Text university;
    private Text course;
    private FloatWritable rating;

    public RatingWritable() {
        firstName = new Text();
        lastName = new Text();
        country = new Text();
        city = new Text();
        university = new Text();
        course = new Text();
        rating = new FloatWritable();
    }

    public boolean isUserInformation() {
        return firstName.toString().length() > 0 || lastName.toString().length() > 0 || country.toString().length() > 0 || city.toString().length() > 0 || university.toString().length() > 0 || course.toString().length() > 0;
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        firstName.readFields(input);
        lastName.readFields(input);
        country.readFields(input);
        city.readFields(input);
        university.readFields(input);
        course.readFields(input);
        rating.readFields(input);
    }

    @Override
    public void write(DataOutput output) throws IOException {
        firstName.write(output);
        lastName.write(output);
        country.write(output);
        city.write(output);
        university.write(output);
        course.write(output);
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

    public Text getUniversity() {
        return university;
    }

    public void setUniversity(Text university) {
        this.university = university;
    }

    public Text getCourse() {
      return course;
    }
  
    public void setCourse(Text course) {
        this.course = course;
    }
  
    public FloatWritable getRating() {
        return rating;
    }

    public void setRating(FloatWritable rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((course == null) ? 0 : course.hashCode());
        result = prime * result + ((university == null) ? 0 : university.hashCode());
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
        RatingWritable other = (RatingWritable) obj;
        if (city == null) {
            if (other.city != null) {
                return false;
            }
        } else if (!city.equals(other.city)) {
            return false;
        }
        if (university == null) {
            if (other.university != null) {
                return false;
            }
        } else if (!university.equals(other.university)) {
            return false;
        }
        if (course == null) {
          if (other.course != null) {
              return false;
            }
        } else if (!course.equals(other.course)) {
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
        return "RatingWritable [name=" + firstName + ", lastName=" + lastName + ", country=" + country + ", city=" + city + ", university=" + university + ", course=" + course + ", rating=" + rating + "]";
    }

}
