package wang.flybird.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="DEMO_INFO")
public class DemoInfo {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="id")
	@GenericGenerator(name = "id", strategy = "assigned")
	private String id;
	
	@Column(name="SEX", length=255)
	@Size(min = 4, max = 255)
	private String sex;
	

}
