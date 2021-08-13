package fi.breakwaterworks.trackthatbarbellmobile.common;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    private ControllerCompositionRoot mControllerCompositionRoot;

    public ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(requireActivity()
            );
        }
        return mControllerCompositionRoot;
    }

}