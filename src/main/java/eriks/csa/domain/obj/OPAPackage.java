package eriks.csa.domain.obj;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.List;

@Entity
public class OPAPackage extends PanacheEntity {
    public String packName;
    public String owner;
    public boolean isOpen;
    public boolean isClaimed;
    public String description;
    public Instant creationDate;

    public OPAPackage(String packName, String owner, boolean isOpen, boolean isClaimed, String description, Instant creationDate) {
        this.packName = packName;
        this.owner = owner;
        this.isOpen = isOpen;
        this.isClaimed = isClaimed;
        this.description = description;
        this.creationDate = creationDate;
    }

    public OPAPackage() {
    }

    public static List<OPAPackage> findUnclaimedByOwner(String userName) {
        return find("owner = ?1 and isClaimed = false", userName).list();
    }

    public static Integer totalPacksByUserName(String userName) {
        return (int) count("owner = ?1", userName);
    }

    public static Integer totalOpenPacksByUserName(String userName) {
        return (int) count("isOpen = true and owner = ?1", userName);
    }
}
