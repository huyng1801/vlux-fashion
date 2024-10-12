// src/components/home/Banner.jsx

import React, { useEffect, useState } from 'react';
import Slider from 'react-slick';
import { getAllBanners } from '../../services/home/HomeService'; // Adjust the path as necessary
import 'slick-carousel/slick/slick.css'; 
import 'slick-carousel/slick/slick-theme.css'; 

const Banner = () => {
    const [banners, setBanners] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchBanners = async () => {
            try {
                const fetchedBanners = await getAllBanners();
                setBanners(fetchedBanners);
            } catch (err) {
                setError("Failed to load banners.");
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchBanners();
    }, []);

    if (loading) {
        return <div>Loading banners...</div>;
    }

    if (error) {
        return <div>{error}</div>;
    }

    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
    };

    return (
        <div className="banner-container">
            <Slider {...settings}>
                {banners.map((banner) => (
                    <div key={banner.id} className="banner-slide">
                        <img src={banner.imageUrl} alt={banner.title} /> {/* Ensure banner has 'imageUrl' */}
                    </div>
                ))}
            </Slider>
        </div>
    );
};

export default Banner;
