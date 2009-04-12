package com.itmill.toolkit.demo;

import java.util.Random;

import com.itmill.toolkit.Application;
import com.itmill.toolkit.terminal.ThemeResource;
import com.itmill.toolkit.ui.Button;
import com.itmill.toolkit.ui.ComboBox;
import com.itmill.toolkit.ui.Embedded;
import com.itmill.toolkit.ui.HorizontalLayout;
import com.itmill.toolkit.ui.Label;
import com.itmill.toolkit.ui.NativeSelect;
import com.itmill.toolkit.ui.Slider;
import com.itmill.toolkit.ui.SplitPanel;
import com.itmill.toolkit.ui.Table;
import com.itmill.toolkit.ui.VerticalLayout;
import com.itmill.toolkit.ui.Window;
import com.itmill.toolkit.ui.Window.Notification;

/**
 * Sample application layout, similar (almost identical) to Apple iTunes.
 * 
 * @author IT Mill Ltd.
 * 
 */
@SuppressWarnings("serial")
public class ToolkitTunesLayout extends Application {

    @Override
    public void init() {

        /*
         * We'll build the whole UI here, since the application will not contain
         * any logic. Otherwise it would be more practical to separate parts of
         * the UI into different classes and methods.
         */

        // Main (browser) window, needed in all Toolkit applications
        final Window root = new Window("ToolkitTunes");

        /*
         * We'll attach the window to the browser view already here, so we won't
         * forget it later.
         */
        setMainWindow(root);

        root
                .showNotification(
                        "This is an example of how you can do layouts in IT Mill Toolkit.<br/>It is not a working sound player.",
                        Notification.TYPE_HUMANIZED_MESSAGE);

        // Our root window contains one VerticalLayout by default, let's make
        // sure it's 100% sized, and remove unwanted margins
        root.getLayout().setSizeFull();
        root.getLayout().setMargin(false);

        // Top area, containing playback and volume controls, play status, view
        // modes and search
        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setMargin(false, true, false, true); // Enable horizontal margins
        top.setSpacing(true);

        // Let's attach that one straight away too
        root.addComponent(top);

        // Create the placeholders for all the components in the top area
        HorizontalLayout playback = new HorizontalLayout();
        HorizontalLayout volume = new HorizontalLayout();
        HorizontalLayout status = new HorizontalLayout();
        HorizontalLayout viewmodes = new HorizontalLayout();
        ComboBox search = new ComboBox();

        // Add the components and align them properly
        top.addComponent(playback);
        top.addComponent(volume);
        top.addComponent(status);
        top.addComponent(viewmodes);
        top.addComponent(search);
        top.setComponentAlignment(playback, "middle");
        top.setComponentAlignment(volume, "middle");
        top.setComponentAlignment(status, "middle center");
        top.setComponentAlignment(viewmodes, "middle");
        top.setComponentAlignment(search, "middle");

        /*
         * We want our status area to expand if the user resizes the root
         * window, and we want it to accommodate as much space as there is
         * available. All other components in the top layout should stay fixed
         * sized, so we don't need to specify any expand ratios for them (they
         * will automatically revert to zero after the following line).
         */
        top.setExpandRatio(status, 1.0F);

        // Playback controls
        Button prev = new Button("Previous");
        Button play = new Button("Play/pause");
        Button next = new Button("Next");
        playback.addComponent(prev);
        playback.addComponent(play);
        playback.addComponent(next);
        // Set spacing between the buttons
        playback.setSpacing(true);

        // Volume controls
        Button mute = new Button("mute");
        Slider vol = new Slider();
        vol.setOrientation(Slider.ORIENTATION_HORIZONTAL);
        vol.setWidth("100px");
        Button max = new Button("max");
        volume.addComponent(mute);
        volume.addComponent(vol);
        volume.addComponent(max);

        // Status area
        status.setWidth("80%");
        status.setSpacing(true);

        Button toggleVisualization = new Button("Mode");
        Label timeFromStart = new Label("0:00");

        // We'll need another layout to show currently playing track and
        // progress
        VerticalLayout trackDetails = new VerticalLayout();
        trackDetails.setWidth("100%");
        Label track = new Label("Track Name");
        Label album = new Label("Album Name - Artist");
        track.setWidth(null);
        album.setWidth(null);
        Slider progress = new Slider();
        progress.setOrientation(Slider.ORIENTATION_HORIZONTAL);
        progress.setWidth("100%");
        trackDetails.addComponent(track);
        trackDetails.addComponent(album);
        trackDetails.addComponent(progress);
        trackDetails.setComponentAlignment(track, "center");
        trackDetails.setComponentAlignment(album, "center");

        Label timeToEnd = new Label("-4:46");
        Button jumpToTrack = new Button("Show");

        // Place all components to the status layout and align them properly
        status.addComponent(toggleVisualization);
        status.setComponentAlignment(toggleVisualization, "middle");
        status.addComponent(timeFromStart);
        status.setComponentAlignment(timeFromStart, "bottom");
        status.addComponent(trackDetails);
        status.addComponent(timeToEnd);
        status.setComponentAlignment(timeToEnd, "bottom");
        status.addComponent(jumpToTrack);
        status.setComponentAlignment(jumpToTrack, "middle");

        // Then remember to specify the expand ratio
        status.setExpandRatio(trackDetails, 1.0F);

        // View mode buttons
        Button viewAsTable = new Button("Table");
        Button viewAsGrid = new Button("Grid");
        Button coverflow = new Button("Coverflow");
        viewmodes.addComponent(viewAsTable);
        viewmodes.addComponent(viewAsGrid);
        viewmodes.addComponent(coverflow);

        /*
         * That covers the top bar. Now let's move on to the sidebar and track
         * listing
         */

        // We'll need one splitpanel to separate the sidebar and track listing
        SplitPanel bottom = new SplitPanel(SplitPanel.ORIENTATION_HORIZONTAL);
        root.addComponent(bottom);

        // The splitpanel is by default 100% x 100%, but we'll need to adjust
        // our main window layout to accomodate the height
        ((VerticalLayout) root.getLayout()).setExpandRatio(bottom, 1.0F);

        // Give the sidebar less space than the listing
        bottom.setSplitPosition(200, SplitPanel.UNITS_PIXELS);

        // Let's add some content to the sidebar
        // First, we need a layout to but all components in
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.setSizeFull();
        bottom.setFirstComponent(sidebar);

        /*
         * Then we need some labels and buttons, and an album cover image The
         * labels and buttons go into their own vertical layout, since we want
         * the 'sidebar' layout to be expanding (cover image in the bottom).
         * VerticalLayout is by default 100% wide.
         */
        VerticalLayout selections = new VerticalLayout();
        Label library = new Label("Library");
        Button music = new Button("Music");
        music.setWidth("100%");

        Label store = new Label("Store");
        Button toolkitTunesStore = new Button("ToolkitTunes Store");
        toolkitTunesStore.setWidth("100%");
        Button purchased = new Button("Purchased");
        purchased.setWidth("100%");

        Label playlists = new Label("Playlists");
        Button genius = new Button("Geniues");
        genius.setWidth("100%");
        Button recent = new Button("Recently Added");
        recent.setWidth("100%");

        // Lets add them to the 'selections' layout
        selections.addComponent(library);
        selections.addComponent(music);
        selections.addComponent(store);
        selections.addComponent(toolkitTunesStore);
        selections.addComponent(purchased);
        selections.addComponent(playlists);
        selections.addComponent(genius);
        selections.addComponent(recent);

        // Then add the selections to the sidebar, and set it expanding
        sidebar.addComponent(selections);
        sidebar.setExpandRatio(selections, 1.0F);

        // Then comes the cover artwork (we'll add the actual image in the
        // themeing section)
        Embedded cover = new Embedded("Currently Playing");
        sidebar.addComponent(cover);

        /*
         * And lastly, we need the track listing table It should fill the whole
         * left side of our bottom layout
         */
        Table listing = new Table();
        listing.setSizeFull();
        listing.setSelectable(true);
        bottom.setSecondComponent(listing);

        // Add the table headers
        listing.addContainerProperty("Name", String.class, "");
        listing.addContainerProperty("Time", String.class, "0:00");
        listing.addContainerProperty("Artist", String.class, "");
        listing.addContainerProperty("Album", String.class, "");
        listing.addContainerProperty("Genre", String.class, "");
        listing.addContainerProperty("Rating", NativeSelect.class,
                new NativeSelect());

        // Lets populate the table with random data
        String[] tracks = new String[] { "Red Flag", "Millstone",
                "Not The Sun", "Breath", "Here We Are", "Deep Heaven",
                "Her Voice Resides", "Natural Tan", "End It All", "Kings",
                "Daylight Slaving", "Mad Man", "Resolve", "Teargas",
                "African Air", "Passing Bird" };
        String[] times = new String[] { "4:12", "6:03", "5:43", "4:32", "3:42",
                "4:45", "2:56", "9:34", "2:10", "3:44", "5:49", "6:30", "5:18",
                "7:42", "3:13", "2:52" };
        String[] artists = new String[] { "Billy Talent", "Brand New",
                "Breaking Benjamin", "Becoming The Archetype",
                "Bullet For My Valentine", "Chasing Victory", "Chimaira",
                "Danko Jones", "Deadlock", "Deftones", "From Autumn To Ashes",
                "Haste The Day", "Four Year Strong", "In Flames", "Kemopetrol",
                "John Legend" };
        String[] albums = new String[] { "Once Again", "The Caitiff Choir",
                "The Devil And God", "Light Grenades", "Dicthonomy",
                "Back In Black", "Dreamer", "Come Clarity", "Year Zero",
                "Frames", "Fortress", "Phobia", "The Poison", "Manifesto",
                "White Pony", "The Big Dirty" };
        String[] genres = new String[] { "Rock", "Metal", "Hardcore", "Indie",
                "Pop", "Alternative", "Blues", "Jazz", "Hip Hop", "Electronica" };
        for (int i = 0; i < 1000; i++) {
            NativeSelect s = new NativeSelect();
            s.addItem("1 star");
            s.addItem("2 stars");
            s.addItem("3 stars");
            s.addItem("4 stars");
            s.addItem("5 stars");
            s.select(new Random().nextInt(5) + " stars");
            listing.addItem(new Object[] {
                    tracks[new Random().nextInt(tracks.length - 1)],
                    times[new Random().nextInt(times.length - 1)],
                    artists[new Random().nextInt(artists.length - 1)],
                    albums[new Random().nextInt(albums.length - 1)],
                    genres[new Random().nextInt(genres.length - 1)], s }, i);
        }

        // We'll align the track time column to right as well
        listing.setColumnAlignment("Time", Table.ALIGN_RIGHT);

        // TODO the footer

        // Now what's left to do? Themeing of course.
        setTheme("toolkittunes");

        /*
         * Let's give a namespace to our application window. This way, if
         * someone uses the same theme for different applications, we don't get
         * unwanted style conflicts.
         */
        root.setStyleName("tTunes");

        top.setStyleName("top");
        top.setHeight("75px"); // Same as the background image height

        playback.setStyleName("playback");
        playback.setMargin(false, true, false, false); // Add right-side margin
        play.setStyleName("play");
        next.setStyleName("next");
        prev.setStyleName("prev");
        playback.setComponentAlignment(prev, "middle");
        playback.setComponentAlignment(next, "middle");

        volume.setStyleName("volume");
        mute.setStyleName("mute");
        max.setStyleName("max");
        vol.setWidth("78px");

        status.setStyleName("status");
        status.setMargin(true);
        status.setHeight("46px"); // Height of the background image

        toggleVisualization.setStyleName("toggle-vis");
        jumpToTrack.setStyleName("jump");

        viewAsTable.setStyleName("viewmode-table");
        viewAsGrid.setStyleName("viewmode-grid");
        coverflow.setStyleName("viewmode-coverflow");

        sidebar.setStyleName("sidebar");

        music.setStyleName("selected");

        cover.setSource(new ThemeResource("images/album-cover.jpg"));
        // Because this is an image, it will retain it's aspect ratio
        cover.setWidth("100%");
    }

}
