import { createRoot } from 'react-dom/client'
import { useState } from 'react'
import './styles.css'
import TextPressure from './TextPressure'
import ShinyText from './ShinyText'

interface SightData {
  sightName: string;
  zone: string;
  category: string;
  photoURL: string;
  address: string;
  description: string;
}

function App(): JSX.Element {
  const [selectedZone, setSelectedZone] = useState<string>('')
  const [sights, setSights] = useState<SightData[]>([])
  const [loading, setLoading] = useState<boolean>(false)
  const [error, setError] = useState<string>('')

  // 基隆七個區域
  const zones = ['中山', '信義', '仁愛', '中正', '安樂', '七堵', '暖暖']

  const loadSights = async (zone: string): Promise<void> => {
    setLoading(true)
    setError('')
    setSelectedZone(zone)
    setSights([])
    
    try {
      const res: Response = await fetch(`/api/sights?zone=${encodeURIComponent(zone)}`)
      if (!res.ok) {
        const msg: string = await res.text()
        throw new Error(msg || `HTTP ${res.status}`)
      }
      const data: SightData[] = await res.json()
      setSights(data)
    } catch (e: any) {
      setSights([])
      setError(e.message || 'An error occurred')
    } finally {
      setLoading(false)
    }
  }

  const openMap = (address: string) => {
    const googleMapsUrl = `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(address)}`
    window.open(googleMapsUrl, '_blank')
  }

  return (
    <div>
      {/* 標題區域 */}
      <div className="title-container">
        <div className="title-wrapper">
          <TextPressure
            text="Keelung Sights"
            flex={true}
            alpha={false}
            stroke={false}
            width={true}
            weight={true}
            italic={true}
            textColor="#ffffff"
            strokeColor="#90ee90"
            minFontSize={24}
          />
        </div>
      </div>
      
      {/* 基隆七個區域按鈕 */}
      <div className="zones-container">
        <h2>🏛️ 選擇基隆行政區</h2>
        <div className="zones-grid">
          {zones.map((zone) => (
            <button 
              key={zone}
              className={`zone-button ${selectedZone === zone ? 'active' : ''}`}
              onClick={() => loadSights(zone)}
              disabled={loading}
            >
              <ShinyText 
                text={`${zone}區`}
                disabled={loading}
                speed={4}
              />
            </button>
          ))}
        </div>
      </div>

      {/* Loading 狀態 */}
      {loading && (
        <div className="loading-container">
          <ShinyText text="🔍 正在搜尋景點..." disabled={false} speed={2} />
        </div>
      )}

      {/* 錯誤訊息 */}
      {error && (
        <div className="error-container">
          <p>❌ {error}</p>
        </div>
      )}

      {/* 景點資料顯示 */}
      {sights.length > 0 && (
        <div className="sights-container">
          <h3>📍 {selectedZone}區景點 ({sights.length} 個)</h3>
          <div className="sights-grid">
            {sights.map((sight, index) => (
              <div key={index} className="sight-card">
                <div className="card-header">
                  <h4>{sight.sightName}</h4>
                  <span className="category-tag">{sight.category}</span>
                </div>
                
                <div className="card-content">
                  <p><strong>🏛️ 區域：</strong>{sight.zone}區</p>
                  <p><strong>📍 地址：</strong>{sight.address}</p>
                  
                  <button 
                    className="address-btn"
                    onClick={() => openMap(sight.address)}
                  >
                    <ShinyText text="🗺️ 查看地圖" disabled={false} speed={3} />
                  </button>
                  
                  {sight.photoURL && sight.photoURL !== "404無照片" && (
                    <div className="image-container">
                      <img src={sight.photoURL} alt={sight.sightName} loading="lazy" />
                    </div>
                  )}
                  
                  {sight.photoURL === "404無照片" && (
                    <p>404無照片</p>
                  )}
                  
                  <div className="description">
                    <p>{sight.description}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  )
}

createRoot(document.getElementById('root')!).render(<App />)