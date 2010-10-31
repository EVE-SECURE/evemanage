package lv.odylab.evemanage.domain.calculation;

import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import lv.odylab.evemanage.domain.user.CharacterInfo;

import javax.persistence.Embedded;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Unindexed
public class CompositeCalculation implements Serializable {
    @Id
    private Long id;
    @Indexed
    private String name;
    private String price;
    @Embedded
    private CharacterInfo attachedCharacterInfo;
    @Indexed
    private String sharingLevel;
    @Indexed
    private Date createdDate;
    @Indexed
    private Date updatedDate;
    private List<CompositeCalculationItem> items;
}
