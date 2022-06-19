package user;

import javafx.scene.image.Image;

/**
 * 
 * A class that manage user in GUI,
 * it extends user and has user image.
 *
 */
public class UserControllerImpl extends UserImpl implements UserController {

    private static final long serialVersionUID = -2758032167630487732L;

    private final transient Image img;

    /**
     * 
     * @param name name of the user
     * @param img image chose by the user
     */
    public UserControllerImpl(final String name, final Image img) {
        super(name);
        this.img = img;
    }

    @Override
    public Image getImage() {
        return this.img;
    }

    @Override
    public User getUser() {
        return new UserImpl(super.getName());
    }
}
