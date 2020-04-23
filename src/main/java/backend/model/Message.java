package backend.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "message")
@NoArgsConstructor
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String message;
	private Long sequence;
	private Long createdBy;
	private ZonedDateTime createdAt;
	private Long updatedBy;
	private ZonedDateTime updatedAt;
	private boolean isDeleted;
	
	@ManyToOne
	@JoinColumn(name="chat_id", nullable=false)
	private Chat chat;
	  
    @PrePersist
    private void prePersist() {
        if (createdAt == null) {
        	createdAt = ZonedDateTime.now();
        }
        if (updatedAt == null) {
        	updatedAt = ZonedDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (updatedAt == null) {
        	updatedAt = ZonedDateTime.now();
        }
    }
}
